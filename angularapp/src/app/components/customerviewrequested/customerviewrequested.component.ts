import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DriverRequest } from 'src/app/models/driver-request.model';
import { DriverRequestService } from 'src/app/services/driver-request.service';
 
@Component({
  selector: 'app-customerviewrequested',
  templateUrl: './customerviewrequested.component.html',
  styleUrls: ['./customerviewrequested.component.css']
})
export class CustomerviewrequestedComponent implements OnInit {
 
  driverRequests: DriverRequest[] = [];
  searchText: string = '';
  selectedRequest: DriverRequest | null = null;
 
 
  // bokya
  // isFetchPayment: boolean = false;
 
  FetchPayment:string='';
 
  requestPaymentDetail: DriverRequest = {};
  isRequestPaymentDetail : boolean = false;
 
 
  constructor(private driverRequestService: DriverRequestService, private router: Router) { }
 
  ngOnInit(): void {
    this.loadDriverRequests();
   
  }
 
  loadDriverRequests(): void {
    this.driverRequestService.getDriverRequestsByUserId(+localStorage.getItem('userId'))
      .subscribe(
        (data) => {
          this.driverRequests = data;
        },
        (error) => {
          console.error('Error fetching driver requests', error);
        }
      );
  }
 
  // searchRequests(): void {
  //   if (this.searchText) {
  //     this.driverRequests = this.driverRequests.filter(request =>
  //       request.driverName.toLowerCase().includes(this.searchText.toLowerCase())
  //     );
  //   } else {
  //     this.loadDriverRequests();
  //   }
  // }
 
  deleteRequest(requestId: number): void {
    if (confirm('Are you sure you want to delete this request?')) {
      this.driverRequestService.deleteDriverRequest(requestId).subscribe(
        () => this.loadDriverRequests(),
        (error) => console.error('Error deleting request', error)
      );
    }
  }
 
  editRequest(driverRequestId:number): void {
    this.router.navigate(['/customer-request'],{queryParams:{driverRequestId:driverRequestId, isEditing:true}});
  }
 
  endTrip(driverRequest: DriverRequest): void {
    if (confirm('Are you sure you want to end this trip?')) {
     
      driverRequest.status = 'tripend';
      const request: DriverRequest = { ...driverRequest };
      // this.isFetchPayment = true;
 
      // this.driverRequestService.updateDriverRequestStatus(request.driverRequestId, 'tripend').subscribe(
      this.driverRequestService.updateDriverRequest(request.driverRequestId, request).subscribe(
        () => this.loadDriverRequests(),
        (error) => console.error('Error ending trip', error)
      );
    }
  }
  // endTrip(request: DriverRequest): void {
  //   if (confirm('Are you sure you want to end this trip?')) {
  //     request.status = 'tripEnd';
 
  //     //bokya
  //     this.isFetchPayment = true;
 
  //     request.actualDropTime = new Date();
  //     const duration = this.calculateDuration(request.tripDate, request.actualDropTime);
  //     const payment = this.calculatePayment(duration, request.paymentAmount || 0);
  //     request.actualDuration = duration;
  //     request.paymentAmount = payment;
 
  //     this.driverRequestService.updateDriverRequest(request.driverRequestId || 0, request).subscribe(
  //       () => this.loadDriverRequests(),
  //       (error) => console.error('Error ending trip', error)
  //     );
  //   }
  // }
 
  showDetails(request: DriverRequest): void {
    this.selectedRequest = request;
  }
 
  // calculateDuration(startDate: Date, endDate: Date): string {
  //   const duration = (new Date(endDate).getTime() - new Date(startDate).getTime()) / (1000 * 60 * 60); // duration in hours
  //   return `${Math.floor(duration)} hours and ${Math.floor((duration % 1) * 60)} minutes`;
  // }
 
  // calculatePayment(duration: string, hourlyRate: number): number {
  //   const durationInHours = parseFloat(duration.split(' ')[0]);
  //   return durationInHours * hourlyRate;
  // }
 
  closeDetails(): void {
    this.selectedRequest = null;
    this.requestPaymentDetail = null;
    this.isRequestPaymentDetail = false;
  }
 
  confirmDelete(requestId: number): void {
    if (confirm('Are you sure you want to delete this request?')) {
      this.deleteRequest(requestId);
    }
  }
 
  paymentPopUp(request: DriverRequest):void{
    this.isRequestPaymentDetail = true;
    this.requestPaymentDetail = request;    
  }
 
  getFormattedDate(dateArray: number[]): string {
    if (!dateArray || dateArray.length < 3) {
      return 'Invalid Date';
    }
    const day = dateArray[0];
    const month = String(dateArray[1]).padStart(2, '0');
    const year = String(dateArray[2]).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
 
  getFormattedTime(timeArray: number[]): string {
    if (!timeArray || timeArray.length < 3) {
      return 'Invalid Time';
    }
    const hours = String(timeArray[0]).padStart(2, '0');
    const minutes = String(timeArray[1]).padStart(2, '0');
    const seconds = String(timeArray[2]).padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
  }
  
  routeToPostFeedback(): void {
    console.log(this.requestPaymentDetail);
    console.log(this.requestPaymentDetail.driver.driverId);  
    this.router.navigate(['/customerpostfeedback'], { queryParams: { driverId: this.requestPaymentDetail.driver.driverId } });
  }
 
}