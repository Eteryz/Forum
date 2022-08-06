import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";

const USER_ROLE_API = environment.apiServerUrl + '/api/userRole/';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) {
  }

  addAdminRoleToUser(username: String) {
    return this.http.get<any>(USER_ROLE_API + 'admin/add/' + username);
  }
}
