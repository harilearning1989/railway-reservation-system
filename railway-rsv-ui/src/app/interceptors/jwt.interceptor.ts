/*
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JwtInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const excludedUrls = ['/auth/login', '/auth/register'];
    const isExcluded = excludedUrls.some((url) => req.url.includes(url));
    if (isExcluded) {
      return next.handle(req);
    }
    const token = localStorage.getItem('token');
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(req);
  }
}
*/

import {HttpInterceptorFn} from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const excludedUrls = ['/auth/login', '/auth/register'];
  const isExcluded = excludedUrls.some((url) => req.url.includes(url));
  if (isExcluded) {
    return next(req);
  }

  const token = localStorage.getItem('token'); // Replace with your storage logic
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    // Log the headers of the modified request
    console.log('Outgoing Request Headers:', authReq.headers.keys());
    authReq.headers.keys().forEach(key => {
      console.log(`${key}: ${authReq.headers.get(key)}`);
    });
    return next(authReq);
  }
  return next(req);
};

