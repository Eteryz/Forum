import {Component, OnInit} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user";
import {ImageCroppedEvent} from "ngx-image-cropper";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  isLoggedIn = false;
  user: any;
  image: any;
  imgChangeEvt: any = '';
  cropImgPreview: any;
  img: any; //для отображения
  visibleIcon: boolean = true;

  constructor(private storageService: StorageService,
              private userService: UserService,
              private router: Router,
              private sanitizer: DomSanitizer) {
  }
  //TODO сделать удаление фото профиля!
  //TODO если меняю username нужно сделать автоматический выход на страницу логина
  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser();
      this.userService.getUser().subscribe(
        (response: User) => {
          this.user = response;
          if(this.user.avatar != null)
            this.showProfileImage(this.user.avatar);
          else
            this.img = '../../assets/images/Profile.png'
        }
      );
    }else {
      this.router.navigate(['/login'])
    }
  }

  saveChanges() {
    console.log(this.user);
    this.userService.updateUser(this.user).subscribe(
      {
        next: (data: User) => {
          this.user = data
        },
        error: err => {
        }
      });
      window.location.reload();
  }

  showProfileImage(buffer: Iterable<number>) {
    let objectURL = 'data:image/jpg;base64,' + buffer;
    this.img = this.sanitizer.bypassSecurityTrustUrl(objectURL);
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
    fd.append('image',this.image,'profile.png');
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
}
