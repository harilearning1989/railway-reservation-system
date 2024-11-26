import {Validators} from "@angular/forms";

export class User {
  id?: number;
  fullName?: string;
  username?: string;
  password?: string;
  email?: string;
  phone?: bigint;
  gender?: string;
  dateOfBirth?: string;
}
