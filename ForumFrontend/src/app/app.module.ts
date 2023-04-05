import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import {HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './component/home/home.component';
import { ArticlesComponent } from './component/articles/articles.component';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import { ArticleComponent } from './component/article/article.component';
import { ProfileComponent } from './component/profile/profile.component';
import { CommentComponent } from './component/comment/comment.component';
import {FormsModule} from "@angular/forms";
import { ArticleCreationComponent } from './component/article-creation/article-creation.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { httpInterceptorProviders } from './helper/http-request-interceptor';
import { ProfileMenuComponent } from './component/profile-menu/profile-menu.component';
import {ImageCropperModule} from "ngx-image-cropper";
import { TemplateArticlesComponent } from './component/template-articles/template-articles.component';
import { MyArticlesComponent } from './component/my-articles/my-articles.component';
import { FavoritesComponent } from './component/favorites/favorites.component';
import { UsersComponent } from './component/users/users.component';
import { ConfirmationDlgComponentComponent } from './component/confirmation-dlg-component/confirmation-dlg-component.component';
import {MatDialogModule} from "@angular/material/dialog";
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {SafeHtmlPipe} from "./pipe/safe-html-pipe";
import { AllUsersComponent } from './component/all-users-component/all-users.component';
import { DeletedUsersComponent } from './component/deleted-users-component/deleted-users.component';
import { ExistingUsersComponent } from './component/existing-users-component/existing-users.component';
import { TemplateUsersComponent } from './component/template-users/template-users.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ArticlesComponent,
    ArticleComponent,
    ProfileComponent,
    CommentComponent,
    ArticleCreationComponent,
    LoginComponent,
    RegisterComponent,
    ProfileMenuComponent,
    TemplateArticlesComponent,
    MyArticlesComponent,
    FavoritesComponent,
    UsersComponent,
    ConfirmationDlgComponentComponent,
    SafeHtmlPipe,
    AllUsersComponent,
    DeletedUsersComponent,
    ExistingUsersComponent,
    TemplateUsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    MatChipsModule,
    MatIconModule,
    ImageCropperModule,
    MatDialogModule,
    CKEditorModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
