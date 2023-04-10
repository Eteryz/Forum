import {A} from "@angular/cdk/keycodes";

export enum EStatus{
  CREATED,
  ACTIVE,
  DELETED
}

export class User {

  public id: string ="";
  public status: EStatus | undefined;
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
