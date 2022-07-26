export class Article {
  public author: string='';
  public id: number | undefined;
  public title: string='';
  public text: string='';
  public likes: number = 0;
  public dislikes: number = 0
  public tag: string=''
  constructor() {
  }

}
