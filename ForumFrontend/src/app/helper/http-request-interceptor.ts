import {Injectable} from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HTTP_INTERCEPTORS,
  HttpErrorResponse
} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {StorageService} from "../service/storage.service";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private  storageService:StorageService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      withCredentials: true,
    });
    return next.handle(req).pipe(
      tap({
          next: (event) => {
          },
          error: (err) => {
            if (err instanceof HttpErrorResponse) {
              if (err.status == 401) {
                console.log('Unauthorized');
                this.storageService.clean();
              }
            }
          }
        }
      )
    );
  }

}

export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},
];
