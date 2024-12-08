import {HttpInterceptorFn} from '@angular/common/http';

export const corsBypassInterceptor: HttpInterceptorFn = (req, next) => {
  const modifiedReq = req.clone({
    setHeaders: {
      'X-Bypass-CORS': 'true', // Example of a custom header
    },
  });
  return next(modifiedReq);
};

