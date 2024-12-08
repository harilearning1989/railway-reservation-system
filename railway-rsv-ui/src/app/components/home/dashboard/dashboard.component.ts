import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {StationDetails} from '../../../models/station-details';
import {StationDetailsService} from '../../../services/station-details.service';
import {ScheduleTrainService} from '../../../services/schedule-train.service';
import {ScheduleTrain} from '../../../models/schedule-train';

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
export class DashboardComponent implements OnInit{

  searchStationText: string = '';
  scheduleTrains: ScheduleTrain[] = [];

  constructor(private scheduleTrainService: ScheduleTrainService) {
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
}
