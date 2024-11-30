import { HttpInterceptorFn } from '@angular/common/http';

import { inject } from '@angular/core';
import {AuthService} from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Define endpoints to exclude
  const excludedUrls = ['/auth/login', '/auth/register']; // Replace with your actual endpoints

  // Check if the current request URL matches any excluded endpoint
  const isExcluded = excludedUrls.some((url) => req.url.includes(url));

  // If the request is excluded, pass it through without modifying
  if (isExcluded) {
    return next(req);
  }

  // Get token from the AuthService (you can implement a caching mechanism in AuthService if needed)
  let token = authService.getToken();

  if (!token) {
    // Fetch the token if not already available
    token = authService.getToken() // Use toPromise() to convert Observable to Promise
  }
  // Clone the request and add the Authorization header
  /*const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });*/

  req = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`)
  });
  // Pass the modified request to the next handler
  return next(req);
};

