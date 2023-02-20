import {environment} from "src/environments/environment.prod";

export class MyUploadAdapter {
  public loader: any;
  //public url: string;
  public xhr: XMLHttpRequest = new XMLHttpRequest();
  //public token: string;

  constructor(loader: any) {
    this.loader = loader;

    // change "environment.BASE_URL" key and API path
   // this.url = `${environment.apiServerUrl}/api/v1/upload/attachments`;

    // change "token" value with your token
    //this.token = localStorage.getItem("token");
  }

  upload() {
    return new Promise(async (resolve, reject) => {
      this.loader.file.then((file: any) => {
        this._initRequest();
        this._initListeners(resolve, reject, file);
        this._sendRequest(file);
      });
    });
  }

  abort() {
    if (this.xhr) {
      this.xhr.abort();
    }
  }

  _initRequest() {
    const xhr = this.xhr;
   // xhr.open("POST", this.url, true);

    // change "Authorization" header with your header
    //xhr.setRequestHeader("Authorization", this.token);

    xhr.responseType = "json";
  }

  _initListeners(resolve: { (value: unknown): void; (arg0: { default: any; }): void; }, reject: { (reason?: any): void; (arg0: string | undefined): any; }, file: { name: any; }) {
    const xhr = this.xhr;
    const loader = this.loader;
    const genericErrorText = "Couldn't upload file:" + ` ${file.name}.`;

    xhr.addEventListener("error", () => reject(genericErrorText));
    xhr.addEventListener("abort", () => reject());

    xhr.addEventListener("load", () => {
      const response = xhr.response;

      if (!response || response.error) {
        return reject(
          response && response.error ? response.error.message : genericErrorText
        );
      }

      // change "response.data.fullPaths[0]" with image URL
      resolve({
        default: response.data.fullPaths[0],
      });
    });

    if (xhr.upload) {
      xhr.upload.addEventListener("progress", (evt) => {
        if (evt.lengthComputable) {
          loader.uploadTotal = evt.total;
          loader.uploaded = evt.loaded;
        }
      });
    }
  }

  _sendRequest(file: string | Blob) {
    const data = new FormData();

    // change "attachments" key
    data.append("attachments", file);

    this.xhr.send(data);
  }
}
