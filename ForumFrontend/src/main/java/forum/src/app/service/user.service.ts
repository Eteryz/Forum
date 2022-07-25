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

  public getUser(username:String): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/findByUsername/`+ username);
  }

  public updateUser(username:String, user: User): Observable<User>{
    return this.http.put<User>(`${this.apiServerUrl}/user/updateByUsername/`+username, user);
  }
}
