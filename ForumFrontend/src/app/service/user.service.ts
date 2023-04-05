import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {environment} from "../../environments/environment.prod";

const USER_API = environment.apiServerUrl + '/api/user/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }


  public getUserInfo(username: string): Observable<User> {
    return this.http.get<User>(USER_API + 'getUserInfo/' + username);
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(USER_API + 'updateByUsername', user);
  }

  updateProfileImage(fd: FormData) {
    return this.http.post<any>(USER_API + 'updateProfileImage', fd);
  }

  deleteAccount() {
    return this.http.delete<String>(USER_API + 'delete/myAccount');
  }

  addAdminRoleToUser(username: String) {
    return this.http.get<any>(USER_API + 'addAdminRole/' + username);
  }

  deleteProfileImage(){
    return this.http.delete<void>(USER_API + 'deleteProfileImage');
  }

  getAllDeleteUsers() {
    return this.http.get<User[]>(USER_API + 'allDeleted');
  }

  getAllExistingUsers(){
    return this.http.get<User[]>(USER_API + 'allExisting');
  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(USER_API + 'all');
  }
}
