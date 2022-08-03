import {Component, OnInit} from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit{

  articles: any;

  constructor(private articleService: ArticleService) {
  }

  public getArticles(): void{
    this.articleService.getAllArticles().subscribe(
      (response: Article[]) => {
        this.articles = response;
      }
    );
  }

  ngOnInit(): void {
      this.getArticles();
  }
}
