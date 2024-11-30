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

  searchSaleText: string = '';
  stationDetails?: StationDetails[];

  constructor(private stationDetailsService: StationDetailsService) {
  }

  ngOnInit(): void {
    console.log('StationDetailsService::');
    this.stationDetailsService.getAll().subscribe((data) => {
      this.stationDetails = data;
      console.log('StationDetailsService getAll::', this.stationDetails);
    });
  }

}
