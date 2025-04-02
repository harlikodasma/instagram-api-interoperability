import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponseDto } from '../models/auth-response.dto';
import { API_BASE_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUrl = API_BASE_URL + '/auth';
  private accessToken: string | null = null;
  private refreshToken: string | null = null;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>(`${this.authBaseUrl}/login`, { username, password });
  }

  tokenRefresh(): Observable<AuthResponseDto> {
    const existingRefreshToken = this.refreshToken;
    if (!existingRefreshToken) {
      throw new Error('Refresh token is missing');
    }
    return this.http.post<AuthResponseDto>(`${this.authBaseUrl}/refresh`, { refreshToken: existingRefreshToken });
  }

  setTokens(accessToken: string, refreshToken: string): void {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  clearTokens(): void {
    this.accessToken = null;
    this.refreshToken = null;
  }

  isAuthenticated(): boolean {
    return !!this.accessToken;
  }
}
