
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  passwordFieldType:string="password";

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  togglePasswordVisibility(): void {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        (tokenDto) => {
          // localStorage.setItem('token', tokenDto.token);

          // console.log('login successfull, Token:'+ tokenDto.token);
          
          // console.log(jwtDecode(tokenDto.token));

          const decoder: any = jwtDecode(tokenDto.token);

          localStorage.setItem('token', tokenDto.token);
          localStorage.setItem('role', decoder?.role);
          localStorage.setItem('userId', decoder?.user_id);
          localStorage.setItem('username', decoder?.username);

          this.loginForm.reset();

          setTimeout(() => {
            this.router.navigate(['/home']);
          }, 2000);

        },
        (error) => {
          console.log("Invalid Credentials");
        }
      );
    }
  }

}
