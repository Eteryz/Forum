import { Component, OnInit } from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/Article";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {environment} from "../../../environments/environment.prod";

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
  currentUser: any;

  constructor(private articleService: ArticleService,
              private route: ActivatedRoute,
              private storageService: StorageService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser()
      this.articleService.findArticleById(this.route.snapshot.params['id'])
        .subscribe({
          next: (data: Article) => {
            this.article = data;
          },
          error: err => {
            this.isSignUpFailed = true;
          }
        });
      this.articleService.getAllArticlesFromFavorites().subscribe(
        (data: Article[]) => {
          data.forEach(value => {
              if(value.id == this.article.id){
                this.colorBookmark = environment.colorBookmark;
              }
          })
        });
    } else {
      this.router.navigate(['/login'])
    }
  }

  clickBtnBookmark() {
    if (this.colorBookmark == environment.colorBookmark) {
      this.colorBookmark = null;
      this.articleService.deleteArticleFromFavorites(this.article.id).subscribe();
    } else {
      this.articleService.addToFavorites(this.article.id).subscribe();
      this.colorBookmark = environment.colorBookmark;

    }
  }

  clickBtnLike() {
    if (this.colorLike=="green")
      this.colorLike = null;
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
