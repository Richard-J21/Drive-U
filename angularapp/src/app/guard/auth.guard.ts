import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
 
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
 
  constructor(private authService: AuthService, private router: Router) { }
 
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):  boolean  {
 
    const expectedRole = route.data.role;
    const presentRole = this.authService.getRole();
 
    if (this.authService.isLoggedIn && presentRole === expectedRole) {
      return true;
    } else {
      this.router.navigate(['/error'])
      console.log('Unauthorized Not Accessible :(');
      return false;
    }
  }
 
}
 