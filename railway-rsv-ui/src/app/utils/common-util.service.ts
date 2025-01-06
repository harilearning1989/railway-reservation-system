import {Injectable} from '@angular/core';
import {Booking} from '../models/booking';

@Injectable({
  providedIn: 'root'
})
export class CommonUtilService {

  constructor() {
  }

  totalTicketBooked(bookings: Booking[]): number {
    return bookings.reduce((sum, booking) => sum + booking.numberOfSeats, 0);
  }
}
