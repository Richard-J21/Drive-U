import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Driver } from '../models/driver.model';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private readonly apiUrl="https://8080-eacadeababedeffaddfeeacfcadcfdab.premiumproject.examly.io/api/driver"

  constructor(private http:HttpClient) { }

  getAllDrivers():Observable<Driver[]>{
    return this.http.get<Driver[]>(this.apiUrl);
  }

  getDriverById(driverId:number):Observable<Driver>{
    return this.http.get<Driver>(`${this.apiUrl}/${driverId}`);
  }

  addDriver(driver:Driver):Observable<Driver>{
    return this.http.post<Driver>(this.apiUrl,driver);
  }

  updateDriver(driverId:number,driver:Driver):Observable<Driver>{
    return this.http.put<Driver>(`${this.apiUrl}/${driverId}`,driver);
  }

  deleteDriver(driverId:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/${driverId}`);
  }

}
