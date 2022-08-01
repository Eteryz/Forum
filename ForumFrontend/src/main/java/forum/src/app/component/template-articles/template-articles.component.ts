import {Component, Input, OnInit} from '@angular/core';
import {ArticleService} from "../../service/article.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";
import {Article} from "../../model/article";
import {combineAll} from "rxjs";

@Component({
  selector: 'app-template-articles',
  templateUrl: './template-articles.component.html',
  styleUrls: ['./template-articles.component.css']
})
export class TemplateArticlesComponent implements OnInit {

  @Input() articles: any;

  public image: string
  public ava: string;
  isLoggedIn = false;
  @Input() search: string='';
  colorBookmark: any;


  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private router: Router) {
    this.image = '../../assets/images/ang1.jpg'
    this.ava = '../../assets/images/avatar1.jpg'
  }

  public getTextArticle(article: Article):string{
    return article.text?.substring(0,100)+"...";
  }

  public getTagsArticle(article: Article): any {
    return article.tag?.split('#');
  }


  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
    }else {
      this.router.navigate(['/login'])
    }
  }

  searchArticles() {
    //TODO сделать поиск статей по title
    this.articles?.filter((value: { tag: string; }) => {
        value.tag.includes(this.search);
      }
    )
    console.log(this.search)
    console.log(this.articles)
  }

  setArticle(id:string) {
    this.router.navigate(['/article/'+id])
  }

  @Input()
  getArticles(): void {

  }

  //при нажатии красятся все кнопки избранное.
  buttonClickBookmark(articleId:string) {
    if(this.colorBookmark=="yellow")
      this.colorBookmark=null;
    else{
      //красить только при хорошем исходе
      console.log(articleId);
      this.articleService.addToFavorites(articleId).subscribe();
      this.colorBookmark = "yellow"
    }
  }

  setColorBookmark($event: any) {

  }
}
