import {Component, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {StorageService} from "../../service/storage.service";
import {CommentService} from "../../service/comment.service";
import {Comment} from "../../model/comment";
import {ActivatedRoute} from "@angular/router";
import {NgForm} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {ImageService} from "../../service/image.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @ViewChild('f', {static: false}) f: NgForm | undefined;

  comments: any;
  listUserPhoto: Map<String, any> = new Map();
  newComment: Comment;

  constructor(private storageService:StorageService,
              private commentService:CommentService,
              private userService: UserService,
              private imageService: ImageService,
              private route: ActivatedRoute) {
    this.newComment = new Comment();
  }

  ngOnInit(): void {
    this.getComments();
  }

  getComments(){
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
    this.commentService.save(this.newComment,this.route.snapshot.params['id']).subscribe(
      {
        next: data => {
          this.addProfilePhotoInList(data);
          this.comments.push(data);
        }
      });
    this.f?.resetForm();
  }

  addProfilePhotoInList(data: any){
    this.userService.getUserInfo(data.author).subscribe(
      value => {
        this.listUserPhoto.set(data.author,
          this.imageService.showProfileImage(value.avatar));
      });
  }

  getUserPhoto(author: string) : any{
    if(this.listUserPhoto.has(author)) {
      return this.listUserPhoto.get(author);
    } else {
      return "..\\..\\assets\\images\\avatar1.jpg"
    }
  }
}
