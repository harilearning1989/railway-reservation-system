import { HttpInterceptorFn } from '@angular/common/http';
import {tap} from 'rxjs';

export const debuggingInterceptor: HttpInterceptorFn = (req, next) => {
  console.log('Outgoing Request:', {
    url: req.url,
    method: req.method,
    headers: req.headers,
  });

  return next(req).pipe(
    tap({
      next: (event) => {
        console.log('Response Event:', event);
      },
      error: (error) => {
        console.error('Error Response:', error);
      },
    })
  );
};
