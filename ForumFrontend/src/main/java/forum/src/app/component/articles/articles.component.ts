import {Component, OnInit, Pipe, PipeTransform} from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit{

  articles: any;

  public image: string
  public ava: string;
  isLoggedIn = false;
  search: string='';

  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private router: Router) {
    this.image = '../../assets/images/ang1.jpg'
    this.ava = '../../assets/images/avatar1.jpg'
  }


  public getArticles(): void{
    this.search ='';
    this.articleService.getAllArticles().subscribe(
      (response: Article[]) => {
        return this.articles = response;
      }
    );
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
      if (this.articles == null)
        this.getArticles();
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
}
