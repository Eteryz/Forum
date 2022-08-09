import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirmation-dlg-component',
  templateUrl: './confirmation-dlg-component.component.html',
  styleUrls: ['./confirmation-dlg-component.component.css']
})
export class ConfirmationDlgComponentComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
  }

}
