import { Component, OnInit } from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.css']
})
export class ArticleCreationComponent implements OnInit {

  article: Article = new Article();

  constructor(private articleService: ArticleService) {

  }

  ngOnInit(): void {
  }

  saveArticle(): void {
    this.articleService.save(this.article).subscribe(
      (response : Article)=>{
        console.log(response);
      }
    )
  }
}
