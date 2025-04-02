import { Router, Routes } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './services/auth.service';
import { LoginComponent } from './components/login/login.component';
import { ValidationComponent } from './components/validation/validation.component';
import { SoapComponent } from './components/soap/soap.component';
import { XmlRpcComponent } from './components/xml-rpc/xml-rpc.component';
import { RestComponent } from './components/rest/rest.component';

export const authGuard = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;
  }

  void router.navigate(['/login']);
  return false;
};

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'validation', pathMatch: 'full' },
      { path: 'validation', component: ValidationComponent },
      { path: 'soap', component: SoapComponent },
      { path: 'xml-rpc', component: XmlRpcComponent },
      { path: 'rest', component: RestComponent }
    ]
  },
  { path: '**', redirectTo: '/login' }
];
