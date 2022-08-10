import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {ImageCroppedEvent} from "ngx-image-cropper";
import {ImageService} from "../../service/image.service";
import {ConfirmationDlgComponentComponent} from "../confirmation-dlg-component/confirmation-dlg-component.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  user: User = new User();
  image: any;
  imgChangeEvt: any = '';
  cropImgPreview: any;
  img: any; //для отображения
  visibleIcon: boolean = true;
  errors = false;

  constructor(private storageService: StorageService,
              private userService: UserService,
              private router: Router,
              private imageService: ImageService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.userService.getUserInfo(this.currentUser.username).subscribe(
      (response: User) => {
        this.user = response;
        if (this.user.avatar != null)
          this.img = this.imageService.showProfileImage(this.user.avatar);
        else
          this.img = '../../assets/images/Profile.png'
      }
    );
  }

  saveChanges() {
    const dlg = this.dialog.open(ConfirmationDlgComponentComponent, {
      data: {title: 'Confirmation', msg: 'Are you sure?'}
    });

    dlg.afterClosed().subscribe((flag: boolean) => {
      if (flag) {
        this.userService.updateUser(this.user).subscribe(
          {
            next: (data: User) => {
              this.user = data
            },
            error: err => {
            }
          });
        if(this.currentUser.username!= this.user.username){
          this.storageService.clean();
        }
      }
      this.userService.getUserInfo(this.currentUser.username).subscribe(
        (response: User) => {
          this.user = response;
        });
    });
  }

  onFileChange(event: any): void {
    this.imgChangeEvt = event;
  }

  cropImg(e: ImageCroppedEvent) {
    this.cropImgPreview = e.base64;
  }

  dataURItoBlob(dataURI: any) {
    const binary = atob(dataURI.split(',')[1]);
    const array = [];
    for (let i = 0; i < binary.length; i++) {
      array.push(binary.charCodeAt(i));
    }
    return new Blob([new Uint8Array(array)], {
      type: 'image/png'
    });
  }

  imgLoad() {
    // display cropper tool
    this.visibleIcon = false;
  }

  initCropper() {
    // init cropper
  }

  imgFailed() {
    // error msg
  }

  saveProfileImage() {
    this.image = this.dataURItoBlob(this.cropImgPreview);
    const fd = new FormData();
    fd.append('image', this.image, 'profile.png');
    this.userService.updateProfileImage(fd).subscribe(
      {
        next: () => {
        },
        error: err => {
        }
      });
    this.clickCloseImageEditor();
    window.location.reload();
  }

  clickCloseImageEditor() {
    this.imgChangeEvt = null;
    this.cropImgPreview = null;
    this.visibleIcon = true;
  }

  deleteAccount() {
    const dlg = this.dialog.open(ConfirmationDlgComponentComponent, {
      data: {title: 'Confirmation', msg: 'Are you sure you want to permanently delete this account?'}
    });

    dlg.afterClosed().subscribe((flag: boolean) => {
      if (flag) {
        this.userService.deleteAccount().subscribe(
          {
            next: value => {
              this.storageService.clean();
            },
            error: err => {
              this.errors = true;
            }
          }
        );
      }
    });

  }

  buttonClickDeleteProfileImage() {
    this.userService.deleteProfileImage().subscribe(
      {
        next: value => {
          window.location.reload();
        }
      }
    );
  }
}
