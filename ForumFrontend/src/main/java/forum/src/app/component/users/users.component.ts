import {Component, OnInit} from '@angular/core';
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

  selectUser: any;

  buttonEnableAddAdminRole = true;

  constructor(private storageService: StorageService,
              private userService: UserService,
  ) {
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      }
    );
  }

  listClick($event: MouseEvent, user: any) {
    user.isSelected = !user.isSelected;
    if (user.isSelected == true) {
      if(this.selectUser!=null){
        this.selectUser.isSelected = false;
      }
      this.buttonEnableAddAdminRole = false;
      this.selectUser = user;
    } else {
      this.buttonEnableAddAdminRole = true;
      this.selectUser = null;
    }
  }

  getColorRow(userId: string): any {
    if (this.selectUser != null && userId == this.selectUser.id) {
      return "greenyellow"
    } else {
      return "white"
    }
  }

  clickButtonAddAdminRole() {
    this.userService.addAdminRoleToUser(this.selectUser.username).subscribe();
    this.buttonEnableAddAdminRole = true;
  }
}
