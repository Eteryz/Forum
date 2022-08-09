export class User {

  public id: string ="";
  public avatar: any = null;
  public name: string ="";
  public username: string ="";
  public email: string ="";
  public phone: string ="";
  public city: string ="";
  public roles: Set<string> = new Set<string>();

  constructor(
    ) {
  }
}
