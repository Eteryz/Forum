import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {Article} from "../../model/Article";
import {environment} from "../../../environments/environment.prod";
import {UserService} from "../../service/user.service";
import {ImageService} from "../../service/image.service";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-template-articles',
  templateUrl: './template-articles.component.html',
  styleUrls: ['./template-articles.component.css']
})
export class TemplateArticlesComponent implements OnInit, OnChanges {

  @Input() articles: any;
  listColorBookmark: Map<String, any> = new Map();
  listUserPhoto: Map<String, any> = new Map();
  public image: string
  search: string = '';
  currentUser: any;


  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private userService: UserService,
              private imageService: ImageService,
              private router: Router) {
    this.image = '../../assets/images/ang1.jpg';

  }

  public getTextArticle(article: Article): string {
    return article.text?.substring(0, 100) + "...";
  }

  public getTagsArticle(article: Article): any {
    return article.tag?.split('#');
  }


  ngOnInit(): void {
      this.currentUser = this.storageService.getUser();
  }

  searchArticles() {
    if (this.search != '') {
      let list: Article[] = [];
      this.articles.forEach((value: Article) => {
          if (value.tag.includes(this.search)
            || value.title.includes(this.search)
            || value.author.includes(this.search)) {
            list.push(value);
          }
        }
      );
      this.articles = list;
    } else {
      this.getArticles();
    }
  }

  setArticle(id: string) {
    this.router.navigate(['/article/' + id])
  }

  @Input()
  getArticles(): void {

  }

  buttonClickBookmark(articleId: string) {
    if (this.listColorBookmark.get(articleId) == environment.colorBookmark) {
      this.listColorBookmark.set(articleId, null);
      this.articleService.deleteArticleFromFavorites(articleId).subscribe();
    } else {
      this.articleService.addToFavorites(articleId).subscribe();
      this.listColorBookmark.set(articleId, environment.colorBookmark);
    }
  }

  getColorBookmark(id: string): any {
    return this.listColorBookmark.get(id);
  }

  getUserPhoto(author: string) : any{
    if (this.listUserPhoto.has(author)) {
      return this.listUserPhoto.get(author);
    } else {
      return "..\\..\\assets\\images\\avatar1.jpg"
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("change");
    const obs = [];
    obs.push(this.articleService.getAllArticlesFromFavorites());
    forkJoin(obs)
      .subscribe(([response1]) => {
        response1.forEach(value => {
          this.listColorBookmark.set(value.id, environment.colorBookmark);
        });
        this.articles.forEach((value: { author: string }) => {
          this.userService.getUserInfo(value.author).subscribe(
            data => {
              if(data.avatar != null){
                this.listUserPhoto.set(value.author,
                  this.imageService.showProfileImage(data.avatar));
              }
            })
        });
      });
  }
}
