import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor(private http: HttpClient) {}

  validate(type: string, xml: string): Observable<{ errors: string[] }> {
    return this.http.post<{ errors: string[] }>(`${API_BASE_URL}/xml/validate/${type}`, xml);
  }
}
