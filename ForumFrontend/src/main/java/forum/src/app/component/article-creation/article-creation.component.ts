import {Component, OnInit} from '@angular/core';
import {Article} from "../../model/article";
import {ArticleService} from "../../service/article.service";
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.css']
})
export class ArticleCreationComponent implements OnInit {

  article: Article = new Article();

  constructor(private articleService: ArticleService) {

  }

  ngOnInit(): void {
  }

  saveArticle(): void {
    this.articleService.save(this.article).subscribe(
      (response: Article) => {
        console.log(response);
      }
    )
  }

  //для тегов
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: String[] = [];

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add our fruit
    if (value) {
      this.tags.push(value);
      this.article.tag += " #" + value;
    }
    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: String): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.article.tag = this.article.tag.replace("${#tag}","")
    }
  }


}
