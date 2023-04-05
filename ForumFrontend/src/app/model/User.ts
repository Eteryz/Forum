import {A} from "@angular/cdk/keycodes";

export class User {

  public id: string ="";
  public avatar: any = null;
  public name: string ="";
  public username: string ="";
  public email: string ="";
  public phone: string ="";
  public location: string ="";
  public roles: string[] = new Array<string>();

  constructor(
    ) {
  }
}
