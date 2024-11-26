import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../../services/login.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
   errorMessage: string = '';
   username: string = '';
   password: string = '';
  constructor(private router: Router,
  private loginService: LoginService) {
  }

  login() {
    this.loginService.login(this.username, this.password).subscribe(
      data => {
        this.router.navigate(['home']);
      },
      error => {
        this.errorMessage = 'Invalid username or password';
      }
    );
  }

  onRegister() {
    // Navigate to the registration page
    this.router.navigate(['/pharmacy-register']);
  }
}
