import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class SoapService {

  constructor(private http: HttpClient) {}

  send(envelope: string): Observable<string> {
    return this.http.post(`${API_BASE_URL}/ws`, envelope, {
      responseType: 'text',
      headers: { 'Content-Type': 'text/xml' }
    });
  }
}
