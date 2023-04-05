import {Component, Input, OnInit} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-template-users',
  templateUrl: './template-users.component.html',
  styleUrls: ['./template-users.component.css']
})
export class TemplateUsersComponent implements OnInit {

  @Input() users: User[] = new Array<User>();

  selectUser: any;
  buttonEnableAddAdminRole = true;

  constructor(private storageService: StorageService,
              private userService: UserService,
  ) {
  }

  @Input()
  getUsers(){

  }

  ngOnInit(): void {

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
