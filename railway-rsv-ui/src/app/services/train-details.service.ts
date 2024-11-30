import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GlobalResponse} from '../models/global-response';
import {TrainDetails} from '../models/train-details';
import {environment} from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TrainDetailsService {

  httpLink = {
    findAll: environment.apiBaseUrl + 'trains/findAll',
  }

  constructor(private http: HttpClient) {
  }

  getAllTrainDetails(): Observable<GlobalResponse<TrainDetails[]>> {
    return this.http.get<GlobalResponse<TrainDetails[]>>(this.httpLink.findAll);
  }

}
