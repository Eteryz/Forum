import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Article} from "../model/Article";

const ARTICLE_API = environment.apiServerUrl + '/api/articles/';
const ARTICLE_RATING_API = environment.apiServerUrl + '/api/articleRating/';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) {
  }

  getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(ARTICLE_API + 'all');
  }

  save(article: Article): Observable<any> {
    return this.http.post<Article>(ARTICLE_API + 'add', article);
  }

  findArticleById(id: string): Observable<Article> {
    return this.http.get<Article>(ARTICLE_API + 'findById/' + id);
  }

  getMyArticles() {
    return this.http.get<Article[]>(ARTICLE_API + 'my');
  }

  addToFavorites(articleId: string) {
    return this.http.get(ARTICLE_API + 'addToFavorites/' + articleId);
  }

  deleteArticleFromFavorites(articleId: string) {
    return this.http.delete(ARTICLE_API + 'deleteArticleFromFavorites/' + articleId);
  }

  getAllArticlesFromFavorites(): Observable<Article[]> {
    return this.http.get<Article[]>(ARTICLE_API + 'allArticlesFromFavorites');
  }

  likeAndDislikeArticle(articleId: string, status: boolean) {
    return this.http.post<any>(ARTICLE_RATING_API + 'likeAndDislikeArticle/' + articleId, status);
  }


  getLikeOrDislikeClickedByUser(articleId: string): Observable<boolean> {
    return this.http.get<boolean>(ARTICLE_RATING_API + 'likeOrDislikeArticle/' + articleId);
  }

}
