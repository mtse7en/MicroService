import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { RequestApi } from './services/request.api';
import { UserApi } from './services/user.api';
import { Globals } from './services/globals';
import { ValidationService } from './services/validation';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { NavMenuComponent } from './components/nav-menu/nav-menu.component';

import { LoginPage } from './components/login/login.component';
import { QuillModule } from 'ngx-quill'
import { NoteComponent } from './components/note/note.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    NoteComponent,
    LoginPage
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'ng-cli-universal' }),
    HttpModule,
    FormsModule,
    BrowserModule,
    FormsModule,
    CommonModule,
    QuillModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: '', component: LoginPage, pathMatch: 'full' },
      { path: 'note', component: NoteComponent },
      
    ])
  ],
  providers: [

    RequestApi,
    UserApi,
    Globals,
    ValidationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
