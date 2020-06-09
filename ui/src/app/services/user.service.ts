import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL = 'http://localhost:9090/users';
  private httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  adduser(user: User) {
    return this.httpClient.post(this.BASE_URL, JSON.stringify(user), this.httpOptions);
  }

  getUsers() {
    return this.httpClient.get(this.BASE_URL);
  }

}
