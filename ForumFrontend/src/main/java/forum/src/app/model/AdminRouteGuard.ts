import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {StorageService} from "../service/storage.service";

@Injectable({
  providedIn: 'root'
})
export class AdminRouteGuard implements CanActivate{

  constructor(private storageService:StorageService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.storageService.getUser().roles.includes('ROLE_ADMIN');
  }
}
