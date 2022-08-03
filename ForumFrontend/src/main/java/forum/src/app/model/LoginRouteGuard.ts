import { Injectable } from '@angular/core';
import {CanActivate} from "@angular/router";
import {StorageService} from "../service/storage.service";

@Injectable({
  providedIn: 'root'
})
export class LoginRouteGuard implements CanActivate{

  constructor(private storageService:StorageService) { }

  canActivate() {
    return this.storageService.isLoggedIn();
  }
}
