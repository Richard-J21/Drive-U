import { Injectable } from '@angular/core';
import { Feedback } from '../models/feedback.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { FeedbackDto } from '../models/feedback-dto.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {


  private apiUrl: string = "https://8080-eacadeababedeffaddfeeacfcadcfdab.premiumproject.examly.io/api/feedback";


  constructor(private http: HttpClient) { }

  sendFeedback(feedbackDto: FeedbackDto): Observable<Feedback>{
    return this.http.post<Feedback>(this.apiUrl, feedbackDto);
  }
 
  getAllFeedbacksByUserId(userId: number): Observable<Feedback[]>{
    return this.http.get<Feedback[]>(`${this.apiUrl}/user/${userId}`);
  }
 
  deleteFeedback(feedbackId: number): Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/${feedbackId}`);
  }
 
  getFeedbacks(): Observable<Feedback[]>{
    return this.http.get<Feedback[]>(this.apiUrl);
  }
}