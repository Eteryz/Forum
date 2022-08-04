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

  article: any;
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
      const obs = [];
      obs.push(this.articleService.findArticleById(this.route.snapshot.params['id']));
      forkJoin(obs)
        .subscribe(([response1]) => {
          this.article = response1;
          this.articleService.getAllArticlesFromFavorites().subscribe(
            (data: Article[]) => {
              data.forEach(value => {
                if (value.id == this.article.id) {
                  this.colorBookmark = environment.colorBookmark;
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
    this.articleService.likeAndDislikeArticle(this.article.id, true).subscribe();
    if (this.colorLike == "green")
      this.colorLike = null;
    else {
      this.colorLike = "green"
      this.colorDislike = null;
      this.article.likes++;
      this.article.dislikes--;
    }
  }

  clickBtnDislike() {
    this.articleService.likeAndDislikeArticle(this.article.id, false).subscribe();
    if (this.colorDislike == "red")
      this.colorDislike = null;
    else {
      this.colorLike = null;
      this.colorDislike = "red"
      this.article.likes--;
      this.article.dislikes++;
    }
  }
}
