import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "../model/article";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) {}

  getAllArticles(): Observable<Article[]>{
    return this.http.get<Article[]>(`${this.apiServerUrl}/articles/all`);
  }

  save(article: Article) : Observable<any> {
    return this.http.post<Article>(`${this.apiServerUrl}/articles/add`, article);
  }

  findArticleById(id: string): Observable<Article>{
    return this.http.get<Article>(`${this.apiServerUrl}/articles/findById/`+ id);
  }

  getMyArticles() {
    return this.http.get<Article[]>(`${this.apiServerUrl}/articles/my`);
  }

  addToFavorites(articleId: string){
    return this.http.get(`${this.apiServerUrl}/articles/addToFavorites/` + articleId);
  }

  deleteArticleFromFavorites(articleId: string){
    return this.http.delete(`${this.apiServerUrl}/articles/deleteArticleFromFavorites/` + articleId);
  }

  getAllArticlesFromFavorites(): Observable<Article[]>{
    return this.http.get<Article[]>(`${this.apiServerUrl}/articles/allArticlesFromFavorites`);
  }

}
