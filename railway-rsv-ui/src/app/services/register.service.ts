import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environment/environment';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  httpLink = {
    login: environment.apiBaseUrl + 'auth/signIn',
    register: environment.apiBaseUrl + 'auth/register/passenger'
  }

  constructor(private http: HttpClient) {
  }

  registerForm(userDetails: User): Observable<User> {
    return this.http.post<any>(this.httpLink.register, userDetails);
  }
}
