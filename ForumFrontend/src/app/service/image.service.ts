import { Injectable } from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private sanitizer: DomSanitizer) { }

  //делает из массива байт фото
  showProfileImage(buffer: Iterable<number>) : any{
    let objectURL = 'data:image/jpg;base64,' + buffer;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }
}
