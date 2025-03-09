import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Driver } from 'src/app/models/driver.model';
import { DriverService } from 'src/app/services/driver.service';
 
@Component({
  selector: 'app-driver-management',
  templateUrl: './driver-management.component.html',
  styleUrls: ['./driver-management.component.css']
})
export class DriverManagementComponent implements OnInit {
[x: string]: any;
 
  constructor(private readonly driverService: DriverService, private readonly formBuilder: FormBuilder, private readonly activatedRouter: ActivatedRoute, private readonly route: Router) { }
 
  driver: Driver = {
    driverName: '',
    licenseNumber: '',
    experienceYears: 0,
    contactNumber: '',
    availabilityStatus: '',
    address: '',
    vehicleType: '',
    hourlyRate: 0,
    image: ''
  };
 
  driverform: FormGroup;
  isEditing: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';
  id: number;
  imageBase64: string;
 
  ngOnInit(): void {
    this.driverform = this.formBuilder.group({
      driverName: ['', [Validators.required,Validators.pattern("^[a-zA-Z\\s]{2,50}$")]],
      licenseNumber: ['', [Validators.required,Validators.pattern("^[A-Za-z]{2}[0-9]{13}$")]],
      experienceYears: ['', [Validators.required,Validators.max(50),Validators.min(0)]],
      contactNumber: ['', [Validators.required,Validators.pattern("[0-9]{10}$")]],
      availabilityStatus:['',Validators.required],
      address: ['', Validators.required,Validators.min(5),Validators.max(50)],
      vehicleType: ['', Validators.required],
      hourlyRate: ['', Validators.required],
      image: ['', Validators.required]
     
    });
 
    this.activatedRouter.queryParams.subscribe(params => {
      this.isEditing = params['isEditing'] === 'true';
      this.id = +params['driverId'];
      if (this.isEditing && this.id) {
        this.getDriverById(this.id);
      }
    });
  }
 
  // getDriverById(driverId: number) {
  //   return this.driverService.getDriverById(driverId).subscribe((data) => this.driver = data);
  // }
  getDriverById(driverId: number) {
    this.driverService.getDriverById(driverId).subscribe((data) => {
      this.driver = data;
      this.driverform.patchValue(this.driver);
    });
  }
 
 
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageBase64 = reader.result as string;
        this.driverform.patchValue({
          image: this.imageBase64
        });
      };
    }
  }
 
  addOrEditDriver() {
    if (this.isEditing) {
      this.driverService.updateDriver(this.id, this.driverform.value).subscribe(() => {
        this.successMessage = 'Driver Edited Successfully';
        this.driverform.reset();
        this.showSuccess(this.successMessage);
        this.route.navigate(['/admin-view-drivers']);
      },
      (error) => this.errorMessage = error.error);
    } else {
      const driverData = {
        ...this.driverform.value,
        image: this.imageBase64
      };
      this.driverService.addDriver(driverData).subscribe(() => {
        this.successMessage = 'Driver Added Successfully!';
        this.driverform.reset();
        this.showSuccess(this.successMessage);
        this.route.navigate(['/admin-view-drivers']);
      }, (error) => this.errorMessage = error.error);
    }
  }
 
 
 
  showSuccess(message: string): void {
    this.successMessage = message;
    setTimeout(() => {
      this.successMessage = '';
    }, 3000);
  }
}