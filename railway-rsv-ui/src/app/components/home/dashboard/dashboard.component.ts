import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ScheduleTrainService} from '../../../services/schedule-train.service';
import {ScheduleTrain} from '../../../models/schedule-train';

declare var bootstrap: any;

@Component({
  selector: 'app-dashboard',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  searchStationText: string = '';
  scheduleTrains: ScheduleTrain[] = [];
  currentSortField: string = '';
  isAscending: boolean = true;
  scheduleTrainForm!: FormGroup;
  modalInstance: any; // Bootstrap modal instance
  selectedUser: ScheduleTrain | undefined; // The user being edited

  constructor(private scheduleTrainService: ScheduleTrainService,
              private fb: FormBuilder) {
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

  scheduleTrain(train: ScheduleTrain) {
    this.initializeForm();
    this.selectedUser = train;

    // Prepopulate the form with the selected user's data
    this.scheduleTrainForm.patchValue({
      trainName: train.trainName,
      trainNumber: train.trainNumber,
      source: train.source,
      destination: train.destination,
      id: train.id
    });

    // Open the modal
    const modalElement = document.getElementById('popupModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }

  private initializeForm() {
    // Initialize the form
    this.scheduleTrainForm = this.fb.group({
      trainName: [{value: '', disabled: true}],
      trainNumber: [{value: '', disabled: true}],
      source: [{value: '', disabled: true}],
      destination: [{value: '', disabled: true}],
      id: ''
    });
  }

  onSubmit(): void {
    this.modalInstance.hide();
    if (this.scheduleTrainForm.valid) {
      this.modalInstance.hide();
    }
  }

}
