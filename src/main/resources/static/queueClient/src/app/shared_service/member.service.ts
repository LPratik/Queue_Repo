import { Injectable } from '@angular/core';
import {Http,Headers,RequestOptions,Response} from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {Member} from '../member';

@Injectable()
export class MemberService {
  
  private baseUrl:string='http://localhost:8080/api';
  private headers = new Headers({'Content-Type':'application/json'});
  private options = new RequestOptions({headers:this.headers});
  
  constructor(private _http:Http) { }

  getMembers(){

    return this._http.get(this.baseUrl+'/members').map((response:Response)=>response.json)
      .catch(this.errorHandler);
  }

  errorHandler(error:response){
      return Observable.throw(error || "SERVER ERROR");
  }
}
