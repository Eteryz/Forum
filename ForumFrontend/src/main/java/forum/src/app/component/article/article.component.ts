import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/article";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  article: any;
  isSignUpFailed: boolean=false;

  constructor(private articleService: ArticleService,
              private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.articleService.findArticleById(this.route.snapshot.params['id'])
      .subscribe({
        next: (data: Article) => {
          this.article = data;
          console.log(data);
        },
        error: err => {
          this.isSignUpFailed = true;
          console.log(this.isSignUpFailed);
        }
      });
  }

}
