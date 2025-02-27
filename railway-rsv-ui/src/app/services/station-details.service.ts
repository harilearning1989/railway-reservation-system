import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StationDetails} from '../models/station-details';
import {environment} from '../../environment/environment';
import {GlobalResponse} from '../models/global-response';

@Injectable({
  providedIn: 'root'
})
export class StationDetailsService {

  constructor(private http: HttpClient) {
  }

  httpLink = {
    stationDetails: environment.apiBaseUrl + 'stations/findAll'
  }

  getAllStationDetails(): Observable<GlobalResponse<StationDetails[]>> {
    return this.http.get<GlobalResponse<StationDetails[]>>(this.httpLink.stationDetails);
  }

  /*getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.httpLink}/${id}`);
  }

  add(data: any): Observable<any> {
    // @ts-ignore
    return this.http.post<any>(this.httpLink, data);
  }

  update(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.httpLink}/${id}`, data);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.httpLink}/${id}`);
  }*/
}
