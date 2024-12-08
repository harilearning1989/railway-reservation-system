import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {jwtInterceptor} from './interceptors/jwt.interceptor';
import {corsBypassInterceptor} from './interceptors/cors-bypass.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptor, corsBypassInterceptor])),//authInterceptor, errorInterceptor, debuggingInterceptor
    //provideHttpClient(),
    //{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    provideClientHydration(withEventReplay())]
};
