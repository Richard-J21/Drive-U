import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FeedbackDto } from 'src/app/models/feedback-dto.model';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';
 
@Component({
  selector: 'app-customerpostfeedback',
  templateUrl: './customerpostfeedback.component.html',
  styleUrls: ['./customerpostfeedback.component.css']
})
export class CustomerpostfeedbackComponent implements OnInit {
 
  feedbackForm!: FormGroup;
  feedbackDto: FeedbackDto = {};
  driverId:number ;
 
  constructor(private feedbackService: FeedbackService, private formbuilder: FormBuilder,private activatedRouter:ActivatedRoute,private route:Router) {
 
  }
 
  ngOnInit(): void {
    this.feedbackForm = this.formbuilder.group(
      {
        feedbackText: ['',[Validators.required]],
        category: ['',[Validators.required]],
        rating: ['',[Validators.required, Validators.max(5)]]
      }
    )
 
    this.activatedRouter.queryParams.subscribe(params => {
      this.driverId = +params['driverId'];
    });
    // this.driverId = +this.activatedRouter.snapshot.paramMap.get('driverId') ;
  }
 
 
 
  addFeedback(){
    if(this.feedbackForm.valid){
        console.log(this.driverId);
       
      this.feedbackDto = {
        ...this.feedbackForm.value,
        driverId: this.driverId,
        userId: +localStorage.getItem('userId')
      }
 
      this.feedbackService.sendFeedback(this.feedbackDto).subscribe(()=>{
        this.route.navigate(['/customerviewfeedback']);
        this.feedbackForm.reset();
      })
    }
  }
 
}
 