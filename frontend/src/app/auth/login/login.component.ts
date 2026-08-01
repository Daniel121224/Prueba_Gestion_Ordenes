import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  template: `
    <div class="login-container">
      <h2>Login</h2>
      <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
        <div>
          <label>Username</label>
          <input type="text" formControlName="username">
          <div *ngIf="loginForm.get('username')?.invalid && loginForm.get('username')?.touched" class="error">
            Username is required
          </div>
        </div>
        <div>
          <label>Password</label>
          <input type="password" formControlName="password">
          <div *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched" class="error">
            Password is required
          </div>
        </div>
        <button type="submit" [disabled]="loginForm.invalid || loading">Login</button>
        <div *ngIf="error" class="error">{{ error }}</div>
        <div *ngIf="loading">Loading...</div>
      </form>
    </div>
  `,
  styles: [`
    .login-container { max-width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px; }
    .error { color: red; font-size: 0.9em; }
    div { margin-bottom: 15px; }
    label { display: block; }
    input { width: 100%; padding: 8px; }
    button { padding: 10px 20px; }
  `]
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;
    this.loading = true;
    this.error = '';
    this.auth.login(this.loginForm.value).subscribe({
      next: () => this.router.navigate(['/orders']),
      error: () => {
        this.loading = false;
        this.error = 'Invalid credentials or server error';
      }
    });
  }
}