import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { CommunityService } from '../community.service';
import { CommunityModel } from '../community-response';

@Component({
  selector: 'app-list-communities',
  templateUrl: './list-communities.component.html',
  styleUrls: ['./list-communities.component.css']
})
export class ListCommunitiesComponent implements OnInit {

  communities: Array<CommunityModel> = [];
  constructor(private communityService: CommunityService) { }

  ngOnInit() {
    this.communityService.getAllCommunities().subscribe(data => {
      this.communities = data;
    }, error => {
      throwError(() => new Error(error))    
      })
  }
}