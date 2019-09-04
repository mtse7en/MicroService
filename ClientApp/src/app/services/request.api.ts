
import { Headers, Response, URLSearchParams, Http, RequestOptionsArgs, RequestOptions, RequestMethod } from '@angular/http';

import { Injectable, Inject } from '@angular/core';


import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { promise } from 'protractor';


@Injectable()
export class RequestApi {

  constructor(private http: Http) {
    this.basePath = "http://localhost:8762" + "/";
  }

  private basePath: string;

  defaultHeaders: Headers = new Headers();

  getRequestBase<TResponse>(params: any, path: string): Observable<TResponse> {

    path = this.basePath + path;
    console.log("START: " + path + " " + JSON.stringify(params));
    let queryParameters = this.objToSearchParams(params);
    let headers = new Headers(this.defaultHeaders.toJSON()); // https://github.com/angular/angular/issues/6845

    let tokenExist = localStorage.getItem('token');
    if (tokenExist != null) {
      headers.append("Authorization", tokenExist);
    }

    let requestOptions: RequestOptionsArgs = new RequestOptions({
      method: RequestMethod.Get,
      headers: headers,
      search: queryParameters,
    });



    return this.http.request(path, requestOptions).pipe(map(r => r.json()));
  }
  authRequest(params: any, path: string): Promise<void> {

    path = this.basePath + path;

    let queryParameters = new URLSearchParams();
    let headers = new Headers(this.defaultHeaders.toJSON());
    headers.append('Content-Type', 'application/json');

    let requestOptions: RequestOptionsArgs = new RequestOptions({
      method: RequestMethod.Post,
      headers: headers,
      search: queryParameters,

    });

    requestOptions.body = params == null ? '' : JSON.stringify(params);

    if (params) {
      requestOptions = this.extendObj(requestOptions, params);
    }

    return this.http.request(path, requestOptions).toPromise().then(x => {
      let token = x.headers.get("authorization");
      if (token != null)
        localStorage.setItem("token", token);
    });

  }
  postRequest<TResponse>(params: any, path: string): Observable<TResponse> {

    path = this.basePath + path;
    console.log("START: " + path + " " + JSON.stringify(params));

    let queryParameters = new URLSearchParams();
    let headers = new Headers(this.defaultHeaders.toJSON()); // https://github.com/angular/angular/issues/6845
    headers.append('Content-Type', 'application/json');

    let tokenExist = localStorage.getItem('token');
    if (tokenExist != null) {
      headers.append("Authorization", tokenExist);
    }

    let requestOptions: RequestOptionsArgs = new RequestOptions({
      method: RequestMethod.Post,
      headers: headers,
      search: queryParameters,

    });

    requestOptions.body = params == null ? '' : JSON.stringify(params);

    if (params) {
      requestOptions = this.extendObj(requestOptions, params);
    }

    return this.http.request(path, requestOptions).pipe(map(r => r.json()));

  }

  private extendObj(objA: any, objB: any) {
    return Object.assign(objA, objB);
  }

  private objToSearchParams(obj: any): URLSearchParams {
    let params: URLSearchParams = new URLSearchParams();
    for (var key in obj) {
      if (obj.hasOwnProperty(key))
        params.set(key, obj[key]);
    }
    return params;
  }


}
