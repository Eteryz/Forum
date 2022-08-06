import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {Comment} from "../model/Comment";

const COMMENT_API = environment.apiServerUrl + '/api/comment/';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  getAllComments(articleId: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(COMMENT_API + articleId + '/all');
  }

  save(comment: Comment, articleId: string): Observable<Comment> {
    return this.http.post<Comment>(COMMENT_API + articleId + '/add', comment);
  }
}
