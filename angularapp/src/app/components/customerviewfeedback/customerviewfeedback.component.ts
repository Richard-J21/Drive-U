import { Component, OnInit } from '@angular/core';
import { Driver } from 'src/app/models/driver.model';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';
 
@Component({
  selector: 'app-customerviewfeedback',
  templateUrl: './customerviewfeedback.component.html',
  styleUrls: ['./customerviewfeedback.component.css']
})
export class CustomerviewfeedbackComponent implements OnInit {
 
  feedbacks: Feedback[] = [];
  driver: Driver = null;
  showDeletePopup: boolean = false;
  selectedDeleteFeedback: Feedback = null;
  errorMessage: string = '';
 
  constructor(private feedbackService: FeedbackService) { }
 
  ngOnInit(): void {
    this.loadFeedbacks()
 
  }
 
  loadFeedbacks(): void {
    this.feedbackService.getAllFeedbacksByUserId(+localStorage.getItem('userId')).subscribe(
      (data) => this.feedbacks = data
    );
  }
 
  viewDriverInfo(feedback: Feedback): void{
    this.driver = feedback.driver;
  }
 
  close(): void{
    this.driver = null;
  }
 
 
  openDeletePopup(feedback: Feedback) {
    this.selectedDeleteFeedback = feedback;
    this.showDeletePopup = true;
  }
 
  confirmDeleteFeedback() {
 
    if (this.selectedDeleteFeedback) {
      this.feedbackService.deleteFeedback(this.selectedDeleteFeedback.feedbackId).subscribe(
        () => {
          this.loadFeedbacks();
          this.closePopup();
        },
        (error) => console.log(error.error)
      );
    }
    this.closePopup();
  }
 
  closePopup() {
    this.showDeletePopup = false;
    this.selectedDeleteFeedback = null;
    this.loadFeedbacks();
  }
}