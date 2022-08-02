import {Component, Input, OnInit} from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {Article} from "../../model/article";

@Component({
  selector: 'app-template-articles',
  templateUrl: './template-articles.component.html',
  styleUrls: ['./template-articles.component.css']
})
export class TemplateArticlesComponent implements OnInit {

  @Input() articles: any;
  colorBookmark: Map<String, any> = new Map();
  public image: string
  public ava: string;
  isLoggedIn = false;
  search: string = '';
  currentUser: any;


  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private router: Router) {
    this.image = '../../assets/images/ang1.jpg'
    this.ava = '../../assets/images/avatar1.jpg'
  }

  public getTextArticle(article: Article): string {
    return article.text?.substring(0, 100) + "...";
  }

  public getTagsArticle(article: Article): any {
    return article.tag?.split('#');
  }


  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser()
      this.articleService.getAllArticlesFromFavorites().subscribe(
        (data: Article[]) => {
          data.forEach(value => this.buttonClickBookmark(value.id));
        }
      );
    } else {
      this.router.navigate(['/login'])
    }
  }

  searchArticles() {
    if (this.search != '') {
      let list: Article[] = [];
      this.articles.forEach((value: Article) => {
          if (value.tag.includes(this.search)
            || value.title.includes(this.search)) {
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
    console.log(this.search);
  }

  buttonClickBookmark(articleId: string) {
    if (this.colorBookmark.get(articleId) == "#FFBE18") {
      this.colorBookmark.set(articleId, null);
      this.articleService.deleteArticleFromFavorites(articleId).subscribe();
    } else {
      this.articleService.addToFavorites(articleId).subscribe();
      this.colorBookmark.set(articleId, "#FFBE18");
    }
  }

  getColorBookmark(id: string): any {
    return this.colorBookmark.get(id);
  }
}
