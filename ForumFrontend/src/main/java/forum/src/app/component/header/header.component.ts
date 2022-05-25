import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public ava: string;
  constructor() {
    this.ava = '../../assets/images/avatar1.jpg'
  }

  ngOnInit(): void {
  }

}
