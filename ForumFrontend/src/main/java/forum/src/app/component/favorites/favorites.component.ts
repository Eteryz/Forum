import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/article";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  articles: any;
  search: string='';

  constructor(private articleService:ArticleService) { }

  ngOnInit(): void {
    if (this.articles == null)
      this.getArticles();
  }

  getArticles() {
    this.search ='';
    this.articleService.getAllArticlesFromFavorites().subscribe(
      (data: Article[]) => {
        return this.articles = data;
      }
    );
  }
}
