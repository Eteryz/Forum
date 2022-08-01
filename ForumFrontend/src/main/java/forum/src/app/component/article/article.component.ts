import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/article";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";

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
  isLoggedIn = false;

  constructor(private articleService: ArticleService,
              private route: ActivatedRoute,
              private storageService: StorageService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
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
    }else {
      this.router.navigate(['/login'])
    }

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
