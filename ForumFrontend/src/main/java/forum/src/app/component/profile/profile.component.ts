import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public users: User[] | undefined;

  constructor(private userService: UserService) {
  }

  public getUsers(): void{
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        return this.users = response;
      }
    );
  }

  ngOnInit(): void {
    this.getUsers();
  }
}
