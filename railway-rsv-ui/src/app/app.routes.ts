import {Routes} from '@angular/router';

export const routes: Routes = [
  {path: 'login', loadComponent: () => import('./components/login/login.component').then(c => c.LoginComponent)},
  {
    path: 'register',
    loadComponent: () => import('./components/register/register.component').then(c => c.RegisterComponent)
  },
  {
    path: 'home', loadComponent: () => import('./components/home/home.component')
      .then(c => c.HomeComponent),
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./components/home/dashboard/dashboard.component').then(
          (m) => m.DashboardComponent
        )
      },
      {
        path: 'station-details',
        loadComponent: () => import('./components/home/station-details/station-details.component').then(c => c.StationDetailsComponent
        )
      },
      {
        path: 'train-details',
        loadComponent: () => import('./components/home/train-details/train-details.component').then(c => c.TrainDetailsComponent)
      },
      {
        path: 'about-us',
        loadComponent: () => import('./components/home/about-us/about-us.component').then(c => c.AboutUsComponent)
      },
      {
        path: 'contact-us',
        loadComponent: () => import('./components/home/contact-us/contact-us.component').then(c => c.ContactUsComponent)
      },
      {path: '**', redirectTo: '/home/dashboard',}
    ]
  },
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login'}];
