import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatError, MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { AuthResponseDto } from '../../models/auth-response.dto';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    MatError,
    NgIf,
    ReactiveFormsModule
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  loginFailed: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username!, password!).subscribe({
        next: (response: AuthResponseDto): void => {
          this.authService.setTokens(response.accessToken, response.refreshToken);
          void this.router.navigate(['/dashboard']);
        },
        error: (): void => {
          this.loginFailed = true;
        }
      });
    }
  }

  loginAsAdmin(): void {
    this.loginForm.patchValue({ username: 'admin', password: 'admin' });
    this.onSubmit();
  }
}
