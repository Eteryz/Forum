import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  recovery: boolean = false;
  flagResponse: boolean = false;
  responseMessage: string ="";

  constructor(private authService: AuthService,
              private storageService: StorageService,
              private router: Router) {

  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.router.navigate(['/home']);
    }
  }

  onSubmit(): void {
    const { username, password } = this.form;
    this.isLoginFailed = false;
    this.flagResponse = false;
    if(this.recovery){
      this.authService.recovery(username,password)
        .subscribe({
          next: data => {
          },
          error: err => {
            this.flagResponse = true;
            this.responseMessage = err.error.text;
          }
        });
    }else{
      this.authService.login(username, password).subscribe({
        next: data => {
          this.storageService.saveUser(data);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.reloadPage();
        },
        error: err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      });
    }
  }

  getResponse(username:string,password:string){

  }

  reloadPage(): void {
    window.location.reload();
  }
}
