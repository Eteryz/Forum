import { Component, OnInit } from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {Article} from "../../model/Article";

@Component({
  selector: 'app-existing-users-component',
  templateUrl: './existing-users.component.html',
  styleUrls: ['./existing-users.component.css']
})
export class ExistingUsersComponent implements OnInit {

  users: any;

  constructor(private userService: UserService) {
  }

  getUsers(){
    this.userService.getAllExistingUsers().subscribe(
        (response: User[]) => {
          this.users = response;
        }
      );
  }

  ngOnInit(): void {
    this.getUsers()
  }



}
