import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {errorInterceptor} from './interceptors/error.interceptor';
import {authInterceptor} from './interceptors/auth.interceptor';
import {debuggingInterceptor} from './interceptors/debugging.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor, debuggingInterceptor])),
    provideHttpClient(),
    provideClientHydration(withEventReplay())]
};
