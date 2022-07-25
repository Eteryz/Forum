import { Component, OnInit } from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  isLoggedIn = false;
  user: any;

  constructor(private storageService: StorageService,
              private userService: UserService,
              private router: Router) {

  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser();
      this.userService.getUser(this.currentUser.username).subscribe(
        (response: User) =>
          this.user = response
      );
    }else {
      this.router.navigate(['/login'])
    }
  }

  saveChanges() {
    this.userService.updateUser(this.currentUser.username, this.user).subscribe(
      (response: User) =>
        this.user = response
    );
    window.location.reload();
  }
}
