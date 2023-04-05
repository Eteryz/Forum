import {A} from "@angular/cdk/keycodes";

export class User {

  public id: string ="";
  public deleted: boolean = false;
  public avatar: number[] = [];
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
