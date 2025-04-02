import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private restBaseUrl: string = API_BASE_URL + `/instagram/user`;
  private restUrlSuffix: string = 'followers';

  constructor(private http: HttpClient) {}

  getUser(username: string): Observable<any> {
    return this.http.get<any>(`${this.restBaseUrl}/${username}/${this.restUrlSuffix}`);
  }

  createUser(username: string, data: any): Observable<any> {
    return this.http.post<any>(`${this.restBaseUrl}/${username}/${this.restUrlSuffix}`, data);
  }

  updateUser(id: number, username: string, data: any): Observable<any> {
    return this.http.put<any>(`${this.restBaseUrl}/${username}/${this.restUrlSuffix}/${id}`, data);
  }

  deleteUser(id: number, username: string): Observable<void> {
    return this.http.delete<void>(`${this.restBaseUrl}/${username}/${this.restUrlSuffix}/${id}`);
  }
}
