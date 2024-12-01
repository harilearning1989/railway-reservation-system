import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';
import {TrainDetails} from '../../../models/train-details';
import {Router} from '@angular/router';
import {TrainDetailsService} from '../../../services/train-details.service';

@Component({
  selector: 'app-schedule-train',
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './schedule-train.component.html',
  styleUrl: './schedule-train.component.scss'
})
export class ScheduleTrainComponent {
  trainDetails: TrainDetails[] = [];
  searchTrainText: string = '';

  constructor(private router: Router,
              private trainDetailsService: TrainDetailsService) {
  }

  ngOnInit(): void {
    this.loadAllTrainDetails();
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
}
