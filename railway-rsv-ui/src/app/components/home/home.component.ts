import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {authInterceptor} from '../../interceptors/auth.interceptor';

@Component({
  selector: 'app-home',
  imports: [
    RouterLink,
    RouterOutlet,
    RouterLinkActive
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private authService: AuthService,
              private router: Router) {
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
