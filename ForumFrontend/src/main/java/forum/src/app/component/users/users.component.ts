import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {StorageService} from "../../service/storage.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: any;

  constructor(private storageService: StorageService,
              private userService: UserService
  ) { }

  ngOnInit(): void {
      this.userService.getUsers().subscribe(
        (response: User[]) => {
          this.users = response;
        }
      );
  }

}
