import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/article";

@Component({
  selector: 'app-my-articles',
  templateUrl: './my-articles.component.html',
  styleUrls: ['./my-articles.component.css']
})
export class MyArticlesComponent implements OnInit {

  articles: any;
  search: string='';

  constructor(private articleService:ArticleService) { }

  ngOnInit(): void {
    if (this.articles == null)
      this.getArticles();
  }

  getArticles() {
    this.search ='';
    this.articleService.getMyArticles().subscribe(
      (response: Article[]) => {
        return this.articles = response;
      }
    );
  }
}
