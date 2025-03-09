import { Component, OnInit } from '@angular/core';
import { DriverRequest } from 'src/app/models/driver-request.model';
import { Driver } from 'src/app/models/driver.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { DriverRequestService } from 'src/app/services/driver-request.service';

@Component({
  selector: 'app-adminviewrequests',
  templateUrl: './adminviewrequests.component.html',
  styleUrls: ['./adminviewrequests.component.css']
})
export class AdminviewrequestsComponent implements OnInit {

  driverRequests: DriverRequest[] = [];
  filteredRequests: DriverRequest[] = [];

  statusFilter: string = '';

  driverDetails: Driver = null;
  driverToggle: boolean = false;
  progressToggle: boolean = false;
  requestProgression: DriverRequest | null = null;

  constructor(private driverRequestService: DriverRequestService, private authService: AuthService) { }

  ngOnInit(): void {
    this.loadDriverRequests();
  }

  loadDriverRequests(): void {
    this.driverRequestService.getAllDriverRequests().subscribe(
      (data) => {
        this.driverRequests = data;
        this.filteredRequests = data;
      },
      (error) => {
        console.error('Error fetching driver requests', error);
      }
    );
  }

  filterByStatus(): void {
    if (this.statusFilter) {
      this.filteredRequests = this.driverRequests.filter(request =>
        request.status.toLowerCase() === this.statusFilter.toLowerCase()
      );
    } else {
      this.filteredRequests = this.driverRequests;
    }
  }

  approveRequest(driverRequest: DriverRequest, driverRequestId: number): void {
    driverRequest.status = 'approved';
    const request: DriverRequest = { ...driverRequest };
    this.driverRequestService.updateDriverRequest(driverRequestId, request).subscribe(
      () => this.loadDriverRequests()
    );
  }

  rejectRequest(driverRequest: DriverRequest, driverRequestId: number): void {
    driverRequest.status = 'rejected';
    const request: DriverRequest = { ...driverRequest };
    this.driverRequestService.updateDriverRequest(driverRequestId, request).subscribe(
      () => this.loadDriverRequests()
    );
  }



  showDriverDetails(request: DriverRequest): void {
    if (this.driverToggle) {
      this.driverDetails = null;
      this.driverToggle = false;
    } else {
      this.driverDetails = request.driver;
      this.driverToggle = true;
    }
  }

  showRequestProgression(request: DriverRequest): void {
    if (this.progressToggle) {
      this.requestProgression = null;
      this.progressToggle = false;
    } else {
      this.requestProgression = request;
      this.progressToggle = true;
    }
  }

  back(): void {
    this.requestProgression = null;
    this.progressToggle = false;
  }

  closeRequest(): void {
    if (this.requestProgression) {
      this.requestProgression.status = 'closed';
      const request: DriverRequest = { ...this.requestProgression };
      this.driverRequestService.updateDriverRequest(this.requestProgression.driverRequestId, request).subscribe(
        () => this.loadDriverRequests()
      );
    }
  }

}