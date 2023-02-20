import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Article} from "../../model/Article";
import {ArticleService} from "../../service/article.service";
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
// @ts-ignore
import * as Editor from "@ckeditor/ckeditor5-36.0.1";
import {MyUploadAdapter} from "../../my-upload-adapter";
import {CKEditor5} from "@ckeditor/ckeditor5-angular";

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.css']
})
export class ArticleCreationComponent implements OnInit {

  form: Article = new Article();
  errorMessage = '';
  //для тегов
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: String[] = [];
  public Editor: any;
  public html: string | undefined;

  constructor(private articleService: ArticleService) {
    this.Editor = Editor;
    this.html = "";
  }

  public onReady(editor: CKEditor5.Editor) {
    editor.plugins.get("FileRepository").createUploadAdapter = (loader: any) => {
      return new MyUploadAdapter(loader);
    };
    editor['ui']
      .getEditableElement()
      .parentElement.insertBefore(
      editor['ui'].view.toolbar.element,
      editor['ui'].getEditableElement()
    );

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

    console.log(this.html);
  }
}
