import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { CommunityModel } from 'src/app/community/community-response';
import { CommunityService } from 'src/app/community/community.service';
import { PostService } from 'src/app/shared/post.service';
import { CreatePostPayload } from './create-post.payload';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup = new FormGroup({});
  postPayload: CreatePostPayload;
  communities: Array<CommunityModel> = [];

  constructor(private router: Router, private postService: PostService, private communityService: CommunityService) {
    this.postPayload = {
      postName: '',
      url: '',
      description: '',
      communityName: ''
    };
  }

  ngOnInit(): void {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      communityName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.communityService.getAllCommunities().subscribe((data) => {
      this.communities = data;
    }, error => {
      throwError(() => new Error(error))    
    })
  }

  createPost() {
    this.postPayload.postName = this.createPostForm.get('postName')?.value;
    this.postPayload.communityName = this.createPostForm.get('communityName')?.value;
    this.postPayload.url = this.createPostForm.get('url')?.value;
    this.postPayload.description = this.createPostForm.get('description')?.value;

    this.postService.createPost(this.postPayload).subscribe((data) => {
      this.router.navigateByUrl('/');
      this.communities = data;
    }, error => {
      throwError(() => new Error(error)) 
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}
