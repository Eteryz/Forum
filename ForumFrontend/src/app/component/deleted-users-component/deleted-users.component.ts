import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-deleted-users-component',
  templateUrl: './deleted-users.component.html',
  styleUrls: ['./deleted-users.component.css']
})
export class DeletedUsersComponent implements OnInit {

  users: any;

  constructor(private userService: UserService,

              ) {
  }

  getUsers(){
    this.userService.getAllDeleteUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      }
    );
  }

  ngOnInit(): void {
    this.getUsers();
  }

}
