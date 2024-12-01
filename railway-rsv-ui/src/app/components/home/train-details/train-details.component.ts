import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TrainDetailsService} from '../../../services/train-details.service';
import {TrainDetails} from '../../../models/train-details';
import {NgForOf} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-train-details',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './train-details.component.html',
  styleUrl: './train-details.component.scss'
})
export class TrainDetailsComponent implements OnInit {

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
