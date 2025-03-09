import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-adminnav',
  templateUrl: './adminnav.component.html',
  styleUrls: ['./adminnav.component.css']
})
export class AdminnavComponent implements OnInit {

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
