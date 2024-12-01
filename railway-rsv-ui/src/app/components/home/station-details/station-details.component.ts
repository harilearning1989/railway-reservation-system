import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {StationDetails} from "../../../models/station-details";
import {StationDetailsService} from '../../../services/station-details.service';

@Component({
  selector: 'app-station-details',
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './station-details.component.html',
  styleUrl: './station-details.component.scss'
})
export class StationDetailsComponent implements OnInit {

  searchStationText: string = '';
  stationDetails: StationDetails[] = [];

  constructor(private stationDetailsService: StationDetailsService) {
  }

  ngOnInit(): void {
    this.loadAllStationDetails();
  }

  loadAllStationDetails(): void {
    this.stationDetailsService.getAllStationDetails().subscribe(response => {
      this.stationDetails = response.data;
      console.log('stationDetails details::', this.stationDetails);
    });
  }

  // Filter data based on searchText
  filteredData() {
    if (!this.searchStationText) {
      return this.stationDetails;
    }
    return this.stationDetails.filter(stationDetail => {
      const searchTerm = this.searchStationText.toLowerCase();
      return (
        stationDetail.stationName?.toLowerCase().includes(searchTerm) ||
        stationDetail.location?.includes(searchTerm) ||
        stationDetail.createdDate?.includes(searchTerm) ||
        stationDetail.updatedDate?.includes(searchTerm) ||
        stationDetail.platformCount?.toString().includes(searchTerm)
      );
    });
  }

}
