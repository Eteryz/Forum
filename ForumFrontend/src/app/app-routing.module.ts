import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {ArticlesComponent} from "./component/articles/articles.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {ArticleCreationComponent} from "./component/article-creation/article-creation.component";
import {RegisterComponent} from "./component/register/register.component";
import {LoginComponent} from "./component/login/login.component";
import {ArticleComponent} from "./component/article/article.component";
import {MyArticlesComponent} from "./component/my-articles/my-articles.component";
import {FavoritesComponent} from "./component/favorites/favorites.component";
import {UsersComponent} from "./component/users/users.component";
import {AdminRouteGuard} from "./model/AdminRouteGuard";
import {LoginRouteGuard} from "./model/LoginRouteGuard";
import {AllUsersComponent} from "./component/all-users-component/all-users.component";
import {DeletedUsersComponent} from "./component/deleted-users-component/deleted-users.component";
import {ExistingUsersComponent} from "./component/existing-users-component/existing-users.component";

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'home', component: HomeComponent},
  { path: '', canActivate: [LoginRouteGuard], children: [
      { path: 'articles', component: ArticlesComponent },
      { path: 'article/:id',component: ArticleComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'article-creation', component: ArticleCreationComponent },
      { path: 'my-articles', component: MyArticlesComponent },
      { path: 'favorites', component: FavoritesComponent },
      { path: 'users',
        component: UsersComponent ,
        canActivate: [AdminRouteGuard],
        children: [
          { path: '', redirectTo: 'all', pathMatch:'full'},
          { path: 'all', component: AllUsersComponent },
          { path: 'delete', component: DeletedUsersComponent },
          { path: 'existing', component: ExistingUsersComponent },
        ]
      },
    ]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
