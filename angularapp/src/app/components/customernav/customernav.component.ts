import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-customernav',
  templateUrl: './customernav.component.html',
  styleUrls: ['./customernav.component.css']
})
export class CustomernavComponent implements OnInit {

  showLogoutConfirmation: Boolean = false;
  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

  logOut(): void {
    this.showLogoutConfirmation = true;
  }

  confirmLogout(): void {
    this.showLogoutConfirmation = false;
    console.log("Admin logged out");
    this.authService.logout();
  }

  cancelLogout(): void { this.showLogoutConfirmation = false; }

}
