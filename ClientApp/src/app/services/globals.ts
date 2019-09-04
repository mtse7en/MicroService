import { Injectable, EventEmitter } from "@angular/core";



@Injectable()
export class Globals {

  currentUser: any;
  CreateButtonVisible: boolean = false


  OnCreateNote : EventEmitter<any> = new EventEmitter<any>();

}
