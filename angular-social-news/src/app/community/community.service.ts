import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommunityModel } from './community-response';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {

  constructor(private http: HttpClient) { }

  getAllCommunities(): Observable<Array<CommunityModel>> {
    return this.http.get<Array<CommunityModel>>('http://localhost:8080/api/community')
  }

  createCommunity(communityModel: CommunityModel): Observable<CommunityModel> {
    console.log(communityModel);
    return this.http.post<CommunityModel>('http://localhost:8080/api/community', communityModel);
  } 
}
