import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { Driver } from 'src/app/models/driver.model';

import { DriverService } from 'src/app/services/driver.service';

@Component({

  selector: 'app-admin-view-drivers',

  templateUrl: './admin-view-drivers.component.html',

  styleUrls: ['./admin-view-drivers.component.css']

})

export class AdminViewDriversComponent implements OnInit {

  drivers: Driver[] = [];
  errorMessage: string = '';
  selectedDriver: Driver | null = null;
  showDeletePopup: boolean = false;
  showEditPopup: boolean = false;
  showStatus: boolean = false;



  constructor(private driverService: DriverService, private route: Router) { }

  ngOnInit(): void {

    this.getAllDrivers();

  }

  getAllDrivers() {

    this.driverService.getAllDrivers().subscribe(

      (drivers: Driver[]) => (this.drivers = drivers),

      (error) => (this.errorMessage = error.error)

    );

  }

  openDeletePopup(driver: Driver) {
    this.selectedDriver = driver;
    this.showDeletePopup = true;

  }

  confirmDeleteDriver() {

    if (this.selectedDriver) {

      this.driverService.deleteDriver(this.selectedDriver.driverId).subscribe(
        () => {
          this.getAllDrivers();
          this.closePopup();
        },
        (error) => (this.errorMessage = error.error)
      );
    }
    this.closePopup();
  }

  openEditPopup(driver: Driver) {

    this.selectedDriver = { ...driver }; // Create a copy to avoid direct binding

    this.showEditPopup = true;

  }

  editDriver(driverId: number) {
    this.route.navigate(['/drivers-management'], { queryParams: { isEditing: true, driverId: driverId } });
  }

  confirmEditDriver(driverId: number) {

    if (this.selectedDriver) {


      this.driverService.updateDriver(this.selectedDriver.driverId, this.selectedDriver).subscribe(

        () => {

          this.editDriver(this.selectedDriver.driverId)
          // this.route.navigate(['/driver-management']);
          this.closePopup();
        },

        (error) => (this.errorMessage = error.error)

      );

    }

  }

  openStatus(driver: Driver) {
    this.selectedDriver = driver;
    this.showStatus = true;

  }




  changeStatus(status: string) {
    if (this.selectedDriver) {
      this.selectedDriver.availabilityStatus = status;
      this.driverService.updateDriver(this.selectedDriver.driverId, this.selectedDriver).subscribe(() => { this.getAllDrivers(); this.closePopup(); }, (error) => (this.errorMessage = error.error));
    }
  }

  closePopup() {
    this.showDeletePopup = false;
    this.showEditPopup = false;
    this.showStatus = false;
    this.selectedDriver = null;
    this.getAllDrivers();
  }

}