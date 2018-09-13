import { Component, OnInit } from '@angular/core';
import {MemberService} from '../../shared_service/member.service';
import {Member} from '../../member';


@Component({
  selector: 'app-listmember',
  templateUrl: './listmember.component.html',
  styleUrls: ['./listmember.component.css']
})
export class ListmemberComponent implements OnInit {
  private members:Member[];
  constructor(private _memberService:MemberService) { }

  ngOnInit() {
    this._memberService.getMembers().subscribe((members)=>{
    console.log(members);
    },(error)=> {
      console.log(error);
    }

    )
  }

}
