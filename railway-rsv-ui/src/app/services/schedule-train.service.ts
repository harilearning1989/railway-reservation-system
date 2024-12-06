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
    scheduleTrain: environment.apiBaseUrl + 'schedule/train',
  }

  constructor(private http: HttpClient) {
  }

  scheduleTrain(id: any, dateTime: any): Observable<GlobalResponse<ScheduleTrain[]>> {
    const payload = {id, dateTime}; // Prepare the payload
    return this.http.post<GlobalResponse<ScheduleTrain[]>>(this.httpLink.scheduleTrain, payload);
  }
}
