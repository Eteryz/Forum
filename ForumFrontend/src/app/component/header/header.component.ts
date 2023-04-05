import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../service/storage.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  showArticles = false;
  private roles: string[] | undefined;
  showUsers = false;

  constructor(private storageService: StorageService) {

  }

  ngOnInit(): void {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showArticles = this.roles.includes('ROLE_USER');
      this.showUsers = this.roles.includes('ROLE_ADMIN');
  }

}
