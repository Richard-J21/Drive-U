import { Component, OnInit } from '@angular/core';
import { Driver } from 'src/app/models/driver.model';
import { Feedback } from 'src/app/models/feedback.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { DriverService } from 'src/app/services/driver.service';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {

  feedbacks: Feedback[] = [];
  filteredFeedbacks: Feedback[] = [];
  categoryFilter: string = '';
  date: Date;
  user: User = null;
  driver:Driver=null;

  constructor(private feedbackService: FeedbackService, private authService: AuthService, private driverService: DriverService) { }

  ngOnInit(): void {
    this.loadfeedbacks();
  }

  loadfeedbacks() {
    this.feedbackService.getFeedbacks().subscribe((result) => {
      this.feedbacks = result;
      this.filteredFeedbacks = result;
    },
      (error) => {
        console.error('Error fetching feedbacks', error);
      }
    );
  }


  viewDriverInfo(feedback: Feedback): void {
    this.driver=feedback.driver;
  }

  showProfile(feedback: Feedback): void {
    this.user = feedback.user;
  }


  close(): void {
    this.user = null;
    this.driver=null;
  }



}
