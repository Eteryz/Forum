import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../service/storage.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  showArticles = false;
  isLoggedIn = false;
  private roles: string[] = [];

  constructor(private storageService: StorageService) {

  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showArticles = this.roles.includes('ROLE_USER');
    } else {
      this.showArticles = false;
    }
  }

}
