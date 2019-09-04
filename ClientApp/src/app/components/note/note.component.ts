import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { QuillEditorComponent } from 'ngx-quill';
import { UserApi } from 'src/app/services/user.api';
import { note } from 'src/app/models/note';


@Component({
  selector: 'note-component',
  templateUrl: './note.component.html'
})
export class NoteComponent implements OnInit {
  
  
  async ngOnInit() {

    await this.getUserNotes();
    this.globals.OnCreateNote.subscribe(x => {
      this.onCreateNote();
    });
    this.globals.CreateButtonVisible = true;
  }


  @ViewChild('txtHeader') txtHeader: ElementRef;
  @ViewChild('txtContent') txtContent: QuillEditorComponent;

  private async getUserNotes() {

    let userId = localStorage.getItem("userId");
    let notes = await this.userApi.getUserNotes(userId).toPromise();
    this.leftItems = notes;

  }

  public onCreateNote() {
    let userId = localStorage.getItem("userId");
    let newItem :note = { id: "-1",userId:userId, noteHeader: 'New Item', noteDetail: '' }
    this.leftItems.push(newItem);
    this.currentItem = this.leftItems[this.leftItems.length - 1];
  }

  public leftItems: Array<note> = new Array<note>();

  public currentItem: note;

  onLeftMenuClicked(item:note) {
    this.currentItem = item;

    this.txtHeader.nativeElement.value = item.noteHeader;
    this.txtContent.quillEditor.setContents([]);
    this.txtContent.quillEditor.clipboard.dangerouslyPasteHTML(0, item.noteDetail)
  }

  constructor(private globals: Globals,private userApi: UserApi) {
 
  }

 async onSave() {

    let header = this.txtHeader.nativeElement.value;
    let content = this.txtContent.quillEditor.root.innerHTML;

    this.currentItem.noteHeader = header;
    this.currentItem.noteDetail = content;
    
       
    if(this.currentItem.id =="-1"){
        await this.userApi.createNote(this.currentItem).toPromise();
        await this.getUserNotes();
    }
    else
        this.userApi.updateNote(this.currentItem).toPromise();

  }
}
