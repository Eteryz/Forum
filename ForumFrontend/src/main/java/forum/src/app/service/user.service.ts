import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServerUrl}/user/all`);
  }

  public getUserInfo(username: string): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/getUserInfo/`+ username);
  }

  public updateUser(user: User): Observable<User>{
    return this.http.put<User>(`${this.apiServerUrl}/user/updateByUsername`, user);
  }

  updateProfileImage(fd: FormData) {
    return this.http.post<any>(`${this.apiServerUrl}/user/updateProfileImage`, fd);
  }

}
