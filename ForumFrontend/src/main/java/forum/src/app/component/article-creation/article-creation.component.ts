import {Component, OnInit} from '@angular/core';
import {Article} from "../../model/Article";
import {ArticleService} from "../../service/article.service";
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {StorageService} from "../../service/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.css']
})
export class ArticleCreationComponent implements OnInit {

  form: Article = new Article();
  errorMessage= '';
  //для тегов
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: String[] = [];

  constructor(private articleService: ArticleService,
              private storageService: StorageService,
              private router: Router) {

  }

  ngOnInit(): void {
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add our fruit
    if (value) {
      this.tags.push(value);
      this.form.tag += " #" + value;
    }
    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: String): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
      this.form.tag = this.form.tag?.replace("${#tag}","");
    }
  }

  onSubmit() {
    this.articleService.save(this.form).subscribe(
      {
        next: data => {
          console.log(data);
          window.location.reload();
        },
        error: err => {
          this.errorMessage = err.error.message;
        }
      });
  }
}
