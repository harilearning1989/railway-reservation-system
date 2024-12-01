import {Injectable} from '@angular/core';
import {environment} from '../../environment/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {LoginResponse} from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  httpLink = {
    login: environment.apiBaseUrl + 'auth/login',
    register: environment.apiBaseUrl + 'auth/signUp',
  }

  private roles: string[] = [];
  private username: string = '';
  private authenticated: boolean = false;

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.httpLink.login, {username, password})
      .pipe(map(response => {
        if (response && response.token) {
          localStorage.setItem('currentUser', JSON.stringify({username, token: response.token}));
          localStorage.setItem('token', response.token);
          const payload = JSON.parse(atob(response.token.split('.')[1]));
          this.username = payload.username;
          //this.setRoles(payload?.roles);
          this.authenticated = true;
        }
        return response;
      }));
  }

}
