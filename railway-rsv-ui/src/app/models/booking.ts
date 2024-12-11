import {Passenger} from './passenger';

export interface Booking {
  numberOfSeats: number;
  passengers: Passenger[];
  trainId: number;
  username: string;
}
