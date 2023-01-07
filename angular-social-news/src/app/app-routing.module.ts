import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { CreateCommunityComponent } from './community/create-community/create-community.component';
import { ListCommunitiesComponent } from './community/list-communities/list-communities.component';
import { HomeComponent } from './home/home.component';
import { CreatePostComponent } from './post/create-post/create-post.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'list-communities', component: ListCommunitiesComponent},
  {path: 'create-post', component: CreatePostComponent},
  {path: 'create-community', component: CreateCommunityComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
