import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TrainDetailsService} from '../../../services/train-details.service';
import {TrainDetails} from '../../../models/train-details';

@Component({
  selector: 'app-train-details',
  imports: [],
  templateUrl: './train-details.component.html',
  styleUrl: './train-details.component.scss'
})
export class TrainDetailsComponent implements OnInit {

  trainDetails: TrainDetails[] = [];

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
}
