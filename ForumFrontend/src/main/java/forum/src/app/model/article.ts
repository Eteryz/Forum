export class Article {

  constructor(
    public id: number = 0,
    public title: string = "",
    public text: string = "",
    public likes: number = 0,
    public dislikes: number = 0,
    public tag: string="") {
  }

}
