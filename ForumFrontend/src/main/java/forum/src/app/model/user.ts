import {Byte} from "@angular/compiler/src/util";

export class User {

  public id: number | undefined
  public name: string | undefined;
  public username: string | undefined;
  public email: string | undefined;
  public phone: string | undefined;
  public avatar: Byte[] | undefined;
  public city: string | undefined;

  constructor(
    ) {
  }
}
