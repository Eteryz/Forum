import {Component, OnInit, ViewChild} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {CommentService} from "../../service/comment.service";
import {Comment} from "../../model/comment";
import {ActivatedRoute} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @ViewChild('f', {static: false}) f: NgForm | undefined;

  comments: any;

  newComment: Comment;

  constructor(private storageService:StorageService,
              private commentService:CommentService,
              private route: ActivatedRoute) {
    this.newComment = new Comment();
  }

  ngOnInit(): void {
    this.getComments();
    console.log("---------")
  }

  public getComments(): void{
    console.log(this.route.snapshot.params['id']);
    this.commentService.getAllComments(this.route.snapshot.params['id']).subscribe(
      (response: Comment[]) => {
        console.log(response);
        return this.comments = response;
      }
    );
  }

  editFormatDate(data:any): string {
    return data.toString().replace("T"," ");
  }
//TODO сделать вывод фото профиля в комментарии
  onSubmit() {
    this.newComment.author = this.storageService.getUser().username;
    this.newComment.date_creation = new Date();
    this.commentService.save(this.newComment,this.route.snapshot.params['id']).subscribe(
      {
        next: data => {
          console.log(data);
          this.comments.push(data)
        },
        error: err => {
        }
      });
    this.f?.resetForm();
  }
}
