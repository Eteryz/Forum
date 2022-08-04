import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RoleService {


  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  addAdminRoleToUser(username: String) {
      return this.http.get<any>(`${this.apiServerUrl}/userRole/admin/add/`+ username);
  }
}
