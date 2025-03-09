import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DriverRequest } from 'src/app/models/driver-request.model';
import { Driver } from 'src/app/models/driver.model';
import { DriverRequestService } from 'src/app/services/driver-request.service';
import { DriverService } from 'src/app/services/driver.service';
 
@Component({
  selector: 'app-customerviewdriver',
  templateUrl: './customerviewdriver.component.html',
  styleUrls: ['./customerviewdriver.component.css']
})
export class CustomerviewdriverComponent implements OnInit {
 
  constructor(private driverService: DriverService, private route: Router, private driverRequestService: DriverRequestService) { }
  drivers: Driver[];
  driverRequests: DriverRequest[];
 
  ngOnInit(): void {
    this.getAllDrivers();
    this.getAllDriverRequests();
  }
 
  getAllDrivers() {
    return this.driverService.getAllDrivers().subscribe((data: Driver[]) => { this.drivers = data });
  }
 
  requestDriver(driverId: number) {
    this.route.navigate(['/customer-request'], { queryParams: { driverId: driverId, isEditing: false, driverRequestId: null } });
  }
 
  getAllDriverRequests(): void {
    this.driverRequestService.getAllDriverRequests().subscribe(
      (data) => this.driverRequests = data
    );
  }
 
  getStatusByDriverId(driverId: number): string {
    if (!this.driverRequests) {
      return 'No request found';
    }
    const driverRequest = this.driverRequests.find((request) => request.driverId === driverId);
    return driverRequest ? driverRequest.status : 'No request found';
   
  }
 
 
}