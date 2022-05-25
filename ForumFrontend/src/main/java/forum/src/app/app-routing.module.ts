import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegistrationComponent} from "./component/registration/registration.component";
import {HomeComponent} from "./component/home/home.component";
import {ArticlesComponent} from "./component/articles/articles.component";
import {AppComponent} from "./app.component";
import {ProfileComponent} from "./component/profile/profile.component";

const routes: Routes = [
  //{path: "", redirectTo: "register", pathMatch: "full"},
  {path: "register", component: RegistrationComponent},
  {path: "home", component: HomeComponent},
  {path: "articles", component: ArticlesComponent},
  {path: "profile", component: ProfileComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
