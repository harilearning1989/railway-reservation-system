import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'railway-rsv-ui';
}
