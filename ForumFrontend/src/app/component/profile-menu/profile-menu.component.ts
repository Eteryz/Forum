import {Component, OnInit, ViewChild} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrls: ['./profile-menu.component.css']
})
export class ProfileMenuComponent implements OnInit {

  public ava: any;

  private roles: string[] | undefined;
  showProfileIcon = false;
  showButtonLoginAndLogout = false;

  constructor(private storageService: StorageService,
              private authService: AuthService,
              private userService: UserService,
              private imageService:ImageService,
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showProfileIcon = this.roles.includes('ROLE_USER');
      this.showButtonLoginAndLogout = false;
      this.userService.getUserInfo(this.storageService.getUser().username).subscribe(
        (data: any) => {
          if(data.avatar != null)
            this.ava = this.imageService.showProfileImage(data.avatar);
          else
            this.ava = '../../assets/images/avatar1.jpg';
        }
      );
    } else {
      this.showButtonLoginAndLogout = true;
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();
      },
      error: err => {
        console.log(err);
      }
    });
  }

}
