import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { Login } from '../models/login.model';
import { TokenDTO } from '../models/token-dto.model';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private readonly apiUrl = "https://8080-eacadeababedeffaddfeeacfcadcfdab.premiumproject.examly.io";

  constructor(private http: HttpClient, private router: Router) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/api/register`, user);
  }

  login(login: Login): Observable<TokenDTO> {
    return this.http.post<TokenDTO>(`${this.apiUrl}/api/login`, login);
  }


  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/api/user/${userId}`);
  }


  isAdmin(): boolean {
    return localStorage.getItem('role') === 'ADMIN' ? true : false;
  }

  isCustomer(): boolean {
    return localStorage.getItem('role') === 'CUSTOMER' ? true : false;
  }

  getRole(): string {
    return localStorage.getItem('role');
  }

  getUserId(): number {
    return +localStorage.getItem('userId');
  }

  getUsername(): string {
    return localStorage.getItem('username');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('role');
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/home']);
  }
}
