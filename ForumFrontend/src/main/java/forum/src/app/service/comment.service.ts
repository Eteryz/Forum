import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {Comment} from "../model/Comment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) {}

  getAllComments(articleId : string) : Observable<Comment[]>{
    return this.http.get<Comment[]>(`${this.apiServerUrl}/comment/`+ articleId+'/all');
  }

  save(comment: Comment, articleId: string): Observable<Comment> {
    return this.http.post<Comment>(`${this.apiServerUrl}/comment/`+ articleId +'/add', comment);
  }
}
