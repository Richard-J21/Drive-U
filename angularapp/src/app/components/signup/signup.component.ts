// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Router } from '@angular/router';
// import { User } from 'src/app/models/user.model';
// import { AuthService } from 'src/app/services/auth.service';

// @Component({
//   selector: 'app-signup',
//   templateUrl: './signup.component.html',
//   styleUrls: ['./signup.component.css']
// })
// export class SignupComponent implements OnInit {

//   signUpForm: FormGroup;

//   constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) { }

//   ngOnInit(): void {
//     this.signUpForm = this.fb.group({
//       username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/)]],
//       email: ['', [Validators.required, Validators.email]],
//       mobileNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
//       password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,16}$/)]],
//       confirmPassword: ['', Validators.required]
//       // userRole: ['', Validators.required]
//     }, { validators: this.passwordMatchValidator });
//   }

//   passwordMatchValidator(signUpForm: FormGroup) {
//     const password = signUpForm.get('password').value;
//     const confirmPassword = signUpForm.get('confirmPassword').value;
//     if (password && confirmPassword && password !== confirmPassword) {
//       signUpForm.get('confirmPassword').setErrors({ mismatch: true });
//       signUpForm.setErrors({ mismatch: true });
//     } else {
//       signUpForm.get('confirmPassword').setErrors(null);
//       signUpForm.setErrors(null);
//     }
// }

//   onSubmit(): void {
//     if (this.signUpForm.valid) {
//       const user: User = {
//         ...this.signUpForm.value,
//         userRole: 'CUSTOMER'
//       } 

//       this.authService.register(user).subscribe(
//       // this.authService.register(this.signUpForm.value).subscribe(
//         (response) => {
//           alert("registration successfull");
//           console.log('after registered in database form backend :'+response);          
//           this.signUpForm.reset();
//           this.router.navigate(['/login']);
//         },
//         (error) => console.log('Error: ' + error)
//       );
//     }
//   }


// }


import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.signUpForm = this.fb.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]+$/)]],
      email: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,16}$/)]],
      confirmPassword: ['', Validators.required]
      // userRole: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  };

  onSubmit(): void {
    if (this.signUpForm.valid) {
      const user: User = {
        ...this.signUpForm.value,
        userRole: 'CUSTOMER'
      }

      this.authService.register(user).subscribe(
        (response) => {
          alert("Registration successful!");
          console.log('After registering in the database from the backend:', response);
          this.signUpForm.reset();
          this.router.navigate(['/login']);
        },
        (error) => console.log('Error: ' + error)
      );
    }
  }
}




