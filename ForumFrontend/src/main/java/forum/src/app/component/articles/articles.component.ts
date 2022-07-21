import { Component, OnInit } from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  articles?: Article[];

  public image: string
  public ava: string;
  isLoggedIn = false;

  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private router: Router) {
    this.image = '../../assets/images/ang1.jpg'
    this.ava = '../../assets/images/avatar1.jpg'
  }

  public getArticles(): void{
    this.articleService.findAll().subscribe(
      (response: Article[]) => {
        return this.articles = response;
      }
    );
  }

  public getTextArticle(article: Article):string{
    return article.text.substring(0,100)+"...";
  }

  public getTagsArticle(article: Article):string[]{
    return article.tag.split('#');
  }


  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.getArticles();
    }else {
      this.router.navigate(['/login'])
    }
  }

}
