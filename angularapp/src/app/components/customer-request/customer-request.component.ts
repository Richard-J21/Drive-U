import { Component, OnInit } from '@angular/core';
import { DriverRequestService } from '../../services/driver-request.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DriverRequest } from 'src/app/models/driver-request.model';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user.model';
import { DriverRequestDto } from 'src/app/models/driver-request-dto.model';
 
@Component({
  selector: 'app-customer-request',
  templateUrl: './customer-request.component.html',
  styleUrls: ['./customer-request.component.css']
})
export class CustomerRequestComponent implements OnInit {
 
  constructor(private authService: AuthService, private driverRequestService: DriverRequestService, private formBuilder: FormBuilder, private activatedRouter: ActivatedRoute, private route: Router) { }
 
  driverRequest: DriverRequest = { }
 
  requestForm: FormGroup;
 
  isEditing: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';
  driverId: number;
  id:number;
 
  //Bokya
  user: User = null;
  driverRequestId: number ;
 
 
 
  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      pickupLocation: ['', Validators.required],
      dropLocation: ['', Validators.required],
      tripDate: ['', Validators.required],
      timeSlot: ['', Validators.required],
      estimatedDuration: ['', Validators.required],
      comments: ['', Validators.required]
    })
 
 
    // this.activatedRouter.queryParams.subscribe(params => {
    //   this.isEditing = params['isEditing'] === 'true';
    //   this.id = +params['driverId'];
    //   if (this.isEditing && this.id) {
    //     this.getDriverRequestById(this.id);
    //   }
    // });
 
 
    // this.activatedRouter.queryParams.subscribe(params => {
    //   this.driverId = params['driverId'];
    //   console.log('Driver ID:', this.driverId);
    // });
 
    this.activatedRouter.queryParams.subscribe(params => {
      if(params['isEditing'] === 'true'){
        this.isEditing = true;
      } else{
        this.isEditing = false;
      }
      this.driverId = +params['driverId'];
      this.driverRequestId = +params['driverRequestId']
    });
 
    // this.authService.getUserById(+localStorage.getItem('userId')).subscribe(
    //   (data) => this.user = data
    // )
 
  }
 
  getDriverRequestById(driverRequestId: number) {
    this.driverRequestService.getDriverRequestById(driverRequestId).subscribe((data) => {
      this.driverRequest = data;
      this.requestForm.patchValue(this.driverRequest);
    });
  }
 
  addOrEditDriverRequest() {
    if (!this.requestForm.valid) {
      this.errorMessage = 'Please fill all required fields correctly.';
      return;
    }
 
    const driverRequestDTO: DriverRequestDto = {
      ...this.requestForm.value,
      driverId: this.driverId,
      userId: +localStorage.getItem('userId')
    };
 
    console.log(driverRequestDTO);
 
    if (this.isEditing) {
      this.driverRequestService.updateDriverRequest(this.driverRequestId, this.requestForm.value).subscribe(() => {
        this.successMessage = 'Driver Request Updated Successfully';
        this.requestForm.reset();
        this.route.navigate(['/customer-view-request']);
      }, (error) => this.errorMessage = error.error);
    } else {
      this.driverRequestService.addDriverRequest(driverRequestDTO).subscribe(() => {
        this.successMessage = 'Driver Added Successfully';
        this.requestForm.reset();
        this.route.navigate(['/customer-view-drivers']);
      }, (error) => this.errorMessage = error.error);
    }
  }
}