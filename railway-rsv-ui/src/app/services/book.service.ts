import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {Booking} from '../models/booking';
import {AuthService} from './auth.service';
import {GlobalResponse} from '../models/global-response';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  httpLink = {
    bookTicket: environment.apiBaseUrl + 'ticket/book',
    bookedTickets: environment.apiBaseUrl + 'ticket/findAllBookedTickets'
  }

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  bookTicket(data: Booking, trainId: any): Observable<Booking> {
    console.log('User name::', this.authService.getUsername());
    data.trainId = trainId;
    data.username = <string>this.authService.getUsername();
    console.log('booking data::', data);
    return this.http.post<any>(this.httpLink.bookTicket, data);
  }

  findAllBookedTickets(trainId: number | undefined): Observable<GlobalResponse<Booking[]>> {
    const url = this.httpLink.bookedTickets + '/' + trainId; // Include the path variable in the URL
    return this.http.get<GlobalResponse<Booking[]>>(url);
  }
}
