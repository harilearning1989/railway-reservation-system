import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {GlobalResponse} from '../models/global-response';
import {ScheduleTrain} from '../models/schedule-train';
import {environment} from '../../environment/environment';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScheduleTrainService {

  httpLink = {
    scheduleTrain: environment.apiBaseUrl + 'schedules/schedule',
    scheduledTrains: environment.apiBaseUrl + 'schedules/scheduled',
  }

  constructor(private http: HttpClient) {
  }

  scheduleTrain(id: any, dateTime: any): Observable<GlobalResponse<ScheduleTrain[]>> {
    const payload = {id, dateTime}; // Prepare the payload
    return this.http.post<GlobalResponse<ScheduleTrain[]>>(this.httpLink.scheduleTrain, payload);
  }

  scheduledTrains(): Observable<GlobalResponse<ScheduleTrain[]>> {
    return this.http.get<GlobalResponse<ScheduleTrain[]>>(this.httpLink.scheduledTrains);
  }
}
