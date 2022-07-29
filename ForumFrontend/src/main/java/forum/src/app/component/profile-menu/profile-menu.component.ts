import {Component, OnInit, ViewChild} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrls: ['./profile-menu.component.css']
})
export class ProfileMenuComponent implements OnInit {

  public ava: any;

  private roles: string[] = [];
  showProfileIcon = false;
  isLoggedIn = false;
  showButtonLoginAndLogout = false;

  constructor(private storageService: StorageService,
              private authService: AuthService,
              private router: Router) {
    if(this.ava == null)
        this.ava = '../../assets/images/avatar1.jpg'
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showProfileIcon = this.roles.includes('ROLE_USER');
      this.showButtonLoginAndLogout = false;
    } else {
      this.showButtonLoginAndLogout = true;
      this.showProfileIcon = false;
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
    this.router.navigate(['/login'])
  }

}
