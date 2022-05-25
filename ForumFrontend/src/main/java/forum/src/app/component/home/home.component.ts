import { Component, OnInit } from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public article?: Article;

  constructor(private articleService: ArticleService) {
  }

  ngOnInit(): void {
  }

  saveArticle(addForm: NgForm): void {
    this.articleService.save(addForm.value).subscribe(
      (response: Article)=> {
        console.log(response);
      }
    )
  }
}
