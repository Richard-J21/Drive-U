import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DriverRequest } from '../models/driver-request.model';
import { DriverRequestDto } from '../models/driver-request-dto.model';

@Injectable({
  providedIn: 'root'
})
export class DriverRequestService {

  readonly apiUrl="https://8080-eacadeababedeffaddfeeacfcadcfdab.premiumproject.examly.io/api/driverRequest";

  constructor(private http:HttpClient) { }

  getAllDriverRequests(): Observable<DriverRequest[]> {
    return this.http.get<DriverRequest[]>(`${this.apiUrl}`);
  }
 
  getDriverRequestById(driverRequestId: number): Observable<DriverRequest> {
    return this.http.get<DriverRequest>(`${this.apiUrl}/${driverRequestId}`);
  }
 
  getDriverRequestsByUserId(userId: number): Observable<DriverRequest[]> {
    return this.http.get<DriverRequest[]>(`${this.apiUrl}/user/${userId}`);
  }
 
  getDriverRequestsByDriverId(driverId: number): Observable<DriverRequest[]> {
    return this.http.get<DriverRequest[]>(`${this.apiUrl}/drivers/${driverId}`);
  }
  
  addDriverRequest(driverRequestDto: DriverRequestDto): Observable<DriverRequest> {
    return this.http.post<DriverRequest>(this.apiUrl, driverRequestDto);
  }
 
  updateDriverRequest(driverRequestId: number, driverRequest: DriverRequest): Observable<DriverRequest> {
    return this.http.put<DriverRequest>(`${this.apiUrl}/${driverRequestId}`, driverRequest);
  }
 
  deleteDriverRequest(driverRequestId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${driverRequestId}`);
  }
}