import { Component } from '@angular/core';
import { Globals } from 'src/app/services/globals';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {

  constructor(public globals : Globals) {

  }

  isExpanded = false;

  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }

  createNote(){

    this.globals.OnCreateNote.emit();
  }
}
