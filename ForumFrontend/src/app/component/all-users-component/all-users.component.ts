import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-all-users-component',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {
  users: any;

  constructor(private userService: UserService,

  ) {
  }

  getUsers(){
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      }
    );
  }

  ngOnInit(): void {
    this.getUsers();
  }

}
