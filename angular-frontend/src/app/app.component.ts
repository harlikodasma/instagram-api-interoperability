import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbar } from '@angular/material/toolbar';
import { MatTab, MatTabGroup } from '@angular/material/tabs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatToolbar, MatTabGroup, MatTab, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  constructor(private router: Router) {}

  private routes: string[] = ['validation', 'soap', 'xml-rpc', 'rest'];

  isDashboardRoute(): boolean {
    return this.router.url.startsWith('/dashboard');
  }

  getSelectedTabIndex(): number {
    const url: string = this.router.url;
    if (url.includes('/dashboard/soap')) return 1;
    if (url.includes('/dashboard/xml-rpc')) return 2;
    if (url.includes('/dashboard/rest')) return 3;
    return 0;
  }

  onTabChange(event: any): void {
    void this.router.navigate(['/dashboard', this.routes[event.index]]);
  }
}
