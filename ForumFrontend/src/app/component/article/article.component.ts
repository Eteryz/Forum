import {Component, OnInit} from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {Article} from "../../model/Article";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {environment} from "../../../environments/environment.prod";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  private color:any = "#FFBE18";
  article: any;
  colorBookmark: any;
  colorLike: any;
  colorDislike: any;
  currentUser: any;

  constructor(private articleService: ArticleService,
              private route: ActivatedRoute,
              private storageService: StorageService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.currentUser = this.storageService.getUser()
      this.articleService.findArticleById(this.route.snapshot.params['id'])
        .subscribe({
          next: (response) => {
            this.article = response;
            this.articleService.getAllArticlesFromFavorites().subscribe(
              (data: Article[]) => {
                data.forEach(value => {
                  if (value.id == this.article.id) {
                    this.colorBookmark = this.color;
                  }
                })
              });
            this.articleService.getLikeOrDislikeClickedByUser(this.article.id).subscribe(
              (value) => {
                if (value != null) {
                  if (value) {
                    this.colorLike = "green"
                  } else {
                    this.colorDislike = "red"
                  }
                }
              }
            );
          },
          error: error => {
            this.router.navigate(['/articles']);
          }
        });
    }
  }

  clickBtnBookmark() {
    if (this.colorBookmark == this.color) {
      this.colorBookmark = null;
      this.articleService.deleteArticleFromFavorites(this.article.id).subscribe();
    } else {
      this.articleService.addToFavorites(this.article.id).subscribe();
      this.colorBookmark = this.color;

    }
  }

  clickBtnLike() {
    this.articleService.likeAndDislikeArticle(this.article.id, true).subscribe();
    if (this.colorLike == "green") {
      this.colorLike = null;
      this.article.likes--;
    } else {
      if (this.colorDislike != null) {
        this.article.dislikes--;
        this.colorDislike = null;
      }
      this.colorLike = "green"
      this.article.likes++;

    }
  }

  clickBtnDislike() {
    this.articleService.likeAndDislikeArticle(this.article.id, false).subscribe();
    if (this.colorDislike == "red") {
      this.colorDislike = null;
      this.article.dislikes--;
    } else {
      if (this.colorLike != null) {
        this.colorLike = null;
        this.article.likes--;
      }
      this.article.dislikes++;
      this.colorDislike = "red"
    }
  }
}
