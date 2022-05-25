import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "../model/article";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Article[]>{
    return this.http.get<Article[]>(`${this.apiServerUrl}/article/all`);
  }

  public save(article: Article){
    return this.http.post<Article>(`${this.apiServerUrl}/article/add`,article);
  }
}
