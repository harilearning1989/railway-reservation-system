import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  // Inject Angular Router to navigate on specific errors
  const router = inject(Router);

  // Pass the request and handle errors
  return next(req).pipe(
    catchError((error) => {
      if (error.status === 401) {
        // Handle Unauthorized errors
        console.error('Unauthorized access - Redirecting to login');
        router.navigate(['/login']);
      } else if (error.status === 403) {
        // Handle Forbidden errors
        console.error('Access forbidden');
      } else if (error.status === 404) {
        // Handle Not Found errors
        console.error('Resource not found');
      } else {
        // Log other errors
        console.error('An unexpected error occurred:', error);
      }

      // Re-throw the error to allow other interceptors to handle it
      return throwError(() => error);
    })
  );
};

