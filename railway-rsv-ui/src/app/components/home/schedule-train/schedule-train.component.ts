import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';
import {TrainDetails} from '../../../models/train-details';
import {Router} from '@angular/router';
import {TrainDetailsService} from '../../../services/train-details.service';
import {DateUtilsService} from '../../../utils/date-utils.service';

declare var bootstrap: any;

@Component({
  selector: 'app-schedule-train',
  imports: [
    FormsModule,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './schedule-train.component.html',
  styleUrl: './schedule-train.component.scss'
})
export class ScheduleTrainComponent implements OnInit {
  trainDetails: TrainDetails[] = [];
  searchTrainText: string = '';
  selectedTrain: TrainDetails | undefined;

  selectedDateTime: string | null = null;
  minDateTime: string = '';
  //editForm!: FormGroup;
  modalInstance: any; // Bootstrap modal instance
  selectedUser: TrainDetails | undefined; // The user being edited

  scheduleTrainForm!: FormGroup;

  constructor(private router: Router,
              private trainDetailsService: TrainDetailsService,
              private dateUtilsService: DateUtilsService,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.loadAllTrainDetails();
    const now = new Date();
    this.minDateTime = this.dateUtilsService.getMinDate(now);

    this.initializeForm();
  }

  loadAllTrainDetails(): void {
    this.trainDetailsService.getAllTrainDetails().subscribe(response => {
      this.trainDetails = response.data;
      console.log('customers details::', this.trainDetails);
    });
  }

  // Filter data based on searchText
  filteredData() {
    if (!this.searchTrainText) {
      return this.trainDetails;
    }
    return this.trainDetails.filter(trainDetail => {
      const searchTerm = this.searchTrainText.toLowerCase();
      return (
        trainDetail.trainName?.toLowerCase().includes(searchTerm) ||
        trainDetail.totalSeats?.toString().includes(searchTerm) ||
        trainDetail.fare?.toString().includes(searchTerm) ||
        trainDetail.duration?.toLowerCase().includes(searchTerm) ||
        trainDetail.source?.toLowerCase().includes(searchTerm) ||
        trainDetail.destination?.toLowerCase().includes(searchTerm)
      );
    });
  }

  scheduleTrain(train: TrainDetails) {
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

  onSubmit(): void {
    if (this.scheduleTrainForm.valid) {
      // Get individual fields
      const dateTime = this.scheduleTrainForm.value.dateTime; // Get the date-time field value
      const id = this.scheduleTrainForm.value.id;
      console.log('Date time and id::', {dateTime, id});



      this.modalInstance.hide();
    }
  }

  private initializeForm() {
    // Initialize the form
    this.scheduleTrainForm = this.fb.group({
      trainName: [{value: '', disabled: true}],
      trainNumber: [{value: '', disabled: true}],
      source: [{value: '', disabled: true}],
      destination: [{value: '', disabled: true}],
      id: '',
      dateTime: ''
    });
  }
}
