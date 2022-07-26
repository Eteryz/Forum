import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServerUrl}/user/all`);
  }

  public getUser(): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/getUserInfo`);
  }

  public updateUser(user: User): Observable<User>{
    return this.http.put<User>(`${this.apiServerUrl}/user/updateByUsername`, user);
  }

  updateProfileImage(fd: FormData) {
    return this.http.post<any>(`${this.apiServerUrl}/user/updateProfileImage`, fd);
  }
}
