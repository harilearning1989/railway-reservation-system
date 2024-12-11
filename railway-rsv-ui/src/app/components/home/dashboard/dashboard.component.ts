import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ScheduleTrainService} from '../../../services/schedule-train.service';
import {ScheduleTrain} from '../../../models/schedule-train';
import {BookService} from '../../../services/book.service';

@Component({
  selector: 'app-dashboard',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    NgIf
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  searchStationText: string = '';
  scheduleTrains: ScheduleTrain[] = [];
  currentSortField: string = '';
  isAscending: boolean = true;
  bookTicketForm: FormGroup;
  trainId: number | undefined;

  constructor(private scheduleTrainService: ScheduleTrainService,
              private fb: FormBuilder,
              private bookService: BookService) {
    this.bookTicketForm = this.fb.group({
      numberOfSeats: [1, [Validators.required, Validators.min(1)]],
      passengers: this.fb.array([this.createPassenger()])
    });
  }

  ngOnInit(): void {
    this.scheduledTrains();
  }

  scheduledTrains(): void {
    this.scheduleTrainService.scheduledTrains().subscribe(response => {
      this.scheduleTrains = response.data;
      console.log('stationDetails details::', this.scheduleTrains);
    });
  }

  // Filter data based on searchText
  filteredData() {
    if (!this.searchStationText) {
      return this.scheduleTrains;
    }
    return this.scheduleTrains.filter(stationDetail => {
      const searchTerm = this.searchStationText.toLowerCase();
      return (
        stationDetail.trainName?.toLowerCase().includes(searchTerm) ||
        stationDetail.source?.includes(searchTerm) ||
        stationDetail.destination?.includes(searchTerm) ||
        stationDetail.trainType?.includes(searchTerm) ||
        stationDetail.fare?.toString().includes(searchTerm)
      );
    });
  }

  sortTable(field: string) {
    if (this.currentSortField === field) {
      this.isAscending = !this.isAscending; // Toggle sort direction
    } else {
      this.isAscending = true; // Default to ascending for new field
      this.currentSortField = field;
    }

    this.scheduleTrains.sort((a: any, b: any) => {
      let valueA = a[field];
      let valueB = b[field];

      if (field === 'date') {
        // Parse as Date for date fields
        valueA = new Date(valueA).getTime();
        valueB = new Date(valueB).getTime();
      } else if (typeof valueA === 'string') {
        // Handle string fields case-insensitively
        valueA = valueA.toLowerCase();
        valueB = valueB.toLowerCase();
      }

      if (valueA < valueB) {
        return this.isAscending ? -1 : 1;
      } else if (valueA > valueB) {
        return this.isAscending ? 1 : -1;
      } else {
        return 0;
      }
    });
  }

  get passengers(): FormArray {
    return this.bookTicketForm.get('passengers') as FormArray;
  }

  createPassenger(): FormGroup {
    return this.fb.group({
      name: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(1)]],
      gender: ['', [Validators.required]]
    });
  }

  addPassenger(): void {
    this.passengers.push(this.createPassenger());
  }

  removePassenger(index: number): void {
    this.passengers.removeAt(index);
    // Update the number of seats in the form
    const currentSize = this.passengers.length;
    this.bookTicketForm.get('numberOfSeats')?.setValue(currentSize);
  }

  onSeatsChange(): void {
    const seatCount = this.bookTicketForm.value.numberOfSeats;
    while (this.passengers.length < seatCount) {
      this.addPassenger();
    }
    while (this.passengers.length > seatCount) {
      this.removePassenger(this.passengers.length - 1);
    }
  }

  onSubmit(): void {
    if (this.bookTicketForm.valid) {
      //console.log('Booking Details:', this.bookTicketForm.value);
      const formData = this.bookTicketForm.value;

      this.bookService.bookTicket(formData,this.trainId).subscribe({
        next: (response) => {
          console.log('Booking successful:', response);
          alert('Booking successful!');
        },
        error: (error) => {
          console.error('Error during booking:', error);
          alert('An error occurred while booking. Please try again.');
        }
      });
    } else {
      console.error('Form is invalid');
    }
  }

  preventInvalidKey(event: KeyboardEvent): void {
    if (event.key === '-' || event.key === 'e') {
      event.preventDefault();
    }
  }

  // Reset the form to its default state
  resetForm(train: ScheduleTrain): void {
    this.trainId = train.id;
    this.bookTicketForm.reset(); // Clear all form fields
    this.bookTicketForm.setControl('passengers', this.fb.array([this.createPassenger()])); // Reinitialize with one passenger
    this.bookTicketForm.get('numberOfSeats')?.setValue(1); // Set default seat value
  }

}
