
import { RequestApi } from "./request.api";
import { Injectable, Inject } from '@angular/core';
import { note } from "../models/note";
import { user } from "../models/user";


@Injectable()
export class UserApi {


  

    constructor(private requestApi: RequestApi)
    {

    }

    login(dto: any) {
        return this.requestApi.authRequest(dto, 'auth/');
    }

    updateNote(dto: any) {
        return this.requestApi.postRequest<any>(dto, 'noteservice/updateNote');
    }
    
    deleteNote(dto: any) {
        return this.requestApi.postRequest<any>(dto, 'noteservice/deleteNote');
    }

    createNote(dto: any) {
        return this.requestApi.postRequest<note>(dto, 'noteservice/newNote');
    }

    getUserNotes(userId: string) {
        return this.requestApi.postRequest<Array<note>>({userId:userId},  'userservice/notes');
    }
      
    getUserInfo(eMail: string) {
        return this.requestApi.postRequest<Array<user>>( {eMail:eMail} ,  'userservice/userInfo');
    }
}