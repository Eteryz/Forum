import { Component, OnInit } from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  articles?: Article[];

  public image: string
  public ava: string;

  constructor(private articleService: ArticleService) {
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
    this.getArticles();
  }

}
