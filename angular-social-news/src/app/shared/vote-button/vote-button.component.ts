import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../post-model';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons'
import { VotePayload } from './vote-payload';
import { VoteService } from '../vote.service';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { PostService } from '../post.service';
import { ToastrService } from 'ngx-toastr';
import { VoteType } from './vote-type';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  @Input() post: PostModel = new PostModel;

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  votePayload: VotePayload;
  // upvoteColor: string;
  // downvoteColor: string;

  constructor(private voteService: VoteService, private authService: AuthService, 
    private postService: PostService, private toastr: ToastrService) {

      this.votePayload = {
        voteType: undefined,
        postId: undefined
      }
  }
  
  ngOnInit(): void {
    this.upvoteVoteDetails();
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  private vote() {
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(() => {
      this.upvoteVoteDetails();
    }, error => {
      this.toastr.error("Error: You voted this already");
      throwError(() => new Error(error))    
    })
  }

  private upvoteVoteDetails() {
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    })
  }

}
