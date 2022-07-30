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
  colorBookmark: any;
  colorLike: any;
  colorDislike: any;

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

  clickBtnBookmark() {
    if(this.colorBookmark=="yellow")
      this.colorBookmark=null;
    else
      this.colorBookmark = "yellow"
  }

  clickBtnLike() {
    if (this.colorLike=="green")
      this.colorLike=null;
    else {
      this.colorLike = "green"
      this.colorDislike = null;
    }
  }

  clickBtnDislike() {
    if (this.colorDislike == "red")
      this.colorDislike = null;
    else {
      this.colorLike = null;
      this.colorDislike = "red"
    }
  }
}
