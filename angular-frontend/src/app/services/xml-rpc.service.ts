import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { XML_RPC_BASE_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class XmlRpcService {

  constructor(private http: HttpClient) {}

  send(xml: string): Observable<string> {
    return this.http.post(XML_RPC_BASE_URL, xml, {
      responseType: 'text',
      headers: { 'Content-Type': 'text/xml' }
    });
  }
}
