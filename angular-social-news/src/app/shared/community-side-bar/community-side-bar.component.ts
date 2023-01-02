import { Component } from '@angular/core';
import { CommunityModel } from 'src/app/community/community-response';
import { CommunityService } from 'src/app/community/community.service';

@Component({
  selector: 'app-community-side-bar',
  templateUrl: './community-side-bar.component.html',
  styleUrls: ['./community-side-bar.component.css']
})
export class CommunitySideBarComponent {
  communities: Array<CommunityModel> = [];
  displayViewAll: boolean = false;

  constructor(private communityService: CommunityService) {
    this.communityService.getAllCommunities().subscribe(data => {
      if(data.length >= 4) {
        this.communities = data.slice(0, 3);
        this.displayViewAll = true;
      } else {
        this.communities = data;
      }
    })
  }

}
