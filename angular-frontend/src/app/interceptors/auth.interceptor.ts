import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthResponseDto } from '../models/auth-response.dto';
import { TokenRefreshModalComponent } from '../components/token-refresh-modal/token-refresh-modal.component';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const authService: AuthService = inject(AuthService);
  const dialog: MatDialog = inject(MatDialog);
  const router: Router = inject(Router);

  const token: string | null = authService.getAccessToken();
  let modifiedRequest: HttpRequest<any> = req;

  if (token && !req.url.includes('/auth/refresh')) {
    modifiedRequest = req.clone({ setHeaders: { 'Authorization': `Bearer ${token}` } });
  }

  return next(modifiedRequest).pipe(
    catchError((error: HttpErrorResponse): Observable<never> => {
      if (error.status === 403) {
        openTokenRefreshModal(authService, dialog, router);
      }
      return throwError((): HttpErrorResponse => error);
    })
  );
};

function openTokenRefreshModal(authService: AuthService, dialog: MatDialog, router: Router): void {
  const dialogRef: MatDialogRef<TokenRefreshModalComponent> = dialog.open(TokenRefreshModalComponent);

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      authService.tokenRefresh().subscribe({
        next: (response: AuthResponseDto): void => {
          authService.setTokens(response.accessToken, response.refreshToken);
        },
        error: (): void => {
          authService.clearTokens();
        }
      });
    } else {
      authService.clearTokens();
      void router.navigate(['/login']);
    }
  });
}
