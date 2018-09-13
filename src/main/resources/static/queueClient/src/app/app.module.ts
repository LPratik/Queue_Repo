import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HttpModule} from '@angular/http';

import { AppComponent } from './app.component';
import { ListmemberComponent } from './components/listmember/listmember.component';
import { MemberformComponent } from './components/memberform/memberform.component';
import {MemberService} from './shared_service/member.service';

const appRoutes:Routes=[
  {path:'',component:ListmemberComponent},
  {path:'op',component:MemberformComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    ListmemberComponent,
    MemberformComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [MemberService],
  bootstrap: [AppComponent]
})
export class AppModule { }
