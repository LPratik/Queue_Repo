import { Component, OnInit } from '@angular/core';
import {UserService}  from '../../shared-service/user.service';
import {User}  from '../../user';
import {Router}  from '@angular/router';


@Component({
  selector: 'app-listuser',
  templateUrl: './listuser.component.html',
  styleUrls: ['./listuser.component.css']
})
export class ListuserComponent implements OnInit {
  private users: User[];
  private tempUsers: User[];
  private servUser: User;
  constructor(private _userService: UserService, private _router: Router) { }

  ngOnInit() {
    this._userService.getUsers().subscribe((users) => {
      console.log(users);
      this.tempUsers = users;

      for (var i = 0; i < this.tempUsers.length; i++) {
        if (this.tempUsers[i].state === "SERVICE") {
          this.servUser = (this.tempUsers[i]);
          this.tempUsers.splice(i, 1);
        }
      }
      this.users = this.tempUsers;
    }, (error) => {
      console.log(error);
    });
  }

  deleteUser(user) {
    this._userService.deleteUser(user.id).subscribe((data) => {
      this.users.splice(this.users.indexOf(user), 1);
    }, (error) => {
      console.log(error);
    });
  }

  updateUser(user) {
    this._userService.setter(user);
    this._router.navigate(['/op']);


  }
  newUser() {
    let user = new User();
    this._userService.setter(user);
    this._router.navigate(['/op']);

  }

  callNextUser() {
    this._userService.callNext().subscribe((user) => {
      console.log(user);
      location.reload;
    }, (error) => {
      console.log(error);
      location.reload;
    })
  }
}
