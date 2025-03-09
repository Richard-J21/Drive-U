import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { DriverManagementComponent } from './components/driver-management/driver-management.component';
import { ErrorComponent } from './components/error/error.component';
import { AdminViewDriversComponent } from './components/admin-view-drivers/admin-view-drivers.component';
import { CustomerviewdriverComponent } from './components/customerviewdriver/customerviewdriver.component';
import { CustomerviewrequestedComponent } from './components/customerviewrequested/customerviewrequested.component';
import { CustomerviewfeedbackComponent } from './components/customerviewfeedback/customerviewfeedback.component';
import { AdminviewrequestsComponent } from './components/adminviewrequests/adminviewrequests.component';
import { AdminviewfeedbackComponent } from './components/adminviewfeedback/adminviewfeedback.component';
import { CustomerpostfeedbackComponent } from './components/customerpostfeedback/customerpostfeedback.component';
import { CustomerRequestComponent } from './components/customer-request/customer-request.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomePageComponent },
  { path: 'admin-view-drivers', component: AdminViewDriversComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'customer-view-drivers', component: CustomerviewdriverComponent },
  { path: 'drivers-management', component: DriverManagementComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'customer-view-request', component: CustomerviewrequestedComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'customerviewfeedback', component: CustomerviewfeedbackComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'customerpostfeedback', component: CustomerpostfeedbackComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'customer-request', component: CustomerRequestComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'error', component: ErrorComponent },
  { path: 'admin-view-request', component: AdminviewrequestsComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'admin-view-feedback', component: AdminviewfeedbackComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
