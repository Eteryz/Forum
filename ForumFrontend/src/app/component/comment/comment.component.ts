import {Component, OnInit, ViewChild} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {CommentService} from "../../service/comment.service";
import {Comment} from "../../model/Comment";
import {ActivatedRoute} from "@angular/router";
import {NgForm} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {ImageService} from "../../service/image.service";
import {ConfirmationDlgComponentComponent} from "../confirmation-dlg-component/confirmation-dlg-component.component";
import {MatDialog} from "@angular/material/dialog";
import {User} from "../../model/User";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @ViewChild('f', {static: false}) f: NgForm | undefined;

  comments: Comment[] = new Array<Comment>();
  listUserPhoto: Map<String, any> = new Map();
  newComment: Comment;
  currentUser: User;

  constructor(private storageService: StorageService,
              private commentService: CommentService,
              private userService: UserService,
              private imageService: ImageService,
              private route: ActivatedRoute,
              private dialog: MatDialog) {
    this.newComment = new Comment();
    this.currentUser = this.storageService.getUser();

  }

  ngOnInit(): void {
    this.getComments();
  }

  getComments() {
    this.commentService.getAllComments(this.route.snapshot.params['id']).subscribe(
      (response: Comment[]) => {
        this.comments = response;
        response.forEach(data => {
          this.addProfilePhotoInList(data);
        })
      }
    );
  }

  onSubmit() {
    this.newComment.author = this.storageService.getUser().username;
    this.newComment.date_creation = new Date();
    this.newComment.date_creation.setUTCHours(this.newComment.date_creation.getHours())
    this.commentService.save(this.newComment, this.route.snapshot.params['id']).subscribe(
      {
        next: data => {
          this.addProfilePhotoInList(data);
          this.comments.push(data);
        }
      });
    this.f?.resetForm();
  }

  addProfilePhotoInList(data: any) {
    this.userService.getUserInfo(data.author).subscribe(
      value => {
        if (value.avatar != null) {
          this.listUserPhoto.set(data.author,
            this.imageService.showProfileImage(value.avatar));
        }
      });
  }

  getUserPhoto(author: string): any {
    if (this.listUserPhoto.has(author)) {
      return this.listUserPhoto.get(author);
    } else {
      return "..\\..\\assets\\images\\avatar1.jpg"
    }
  }

  buttonClickDelete(id: string) {
    const dlg = this.dialog.open(ConfirmationDlgComponentComponent, {
      data: {title: 'Confirmation', msg: 'Are you sure you want to permanently delete this article?'}
    });

    dlg.afterClosed().subscribe((flag: boolean) => {
      if (flag) {
        this.commentService.deleteCommentById(id).subscribe({
          next: value => {
            console.log(value);
            this.getComments();
          },
          error: err => {
          }
        });
      }
    });
  }
}
