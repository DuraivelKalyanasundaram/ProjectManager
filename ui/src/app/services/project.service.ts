import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Project } from '../model/Project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private BASE_URL = 'http://localhost:9090/users';
  private httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  addProject(project: Project) {
    return this.httpClient.post(this.BASE_URL, JSON.stringify(project), this.httpOptions);
  }

  getProject() {
    return this.httpClient.get(this.BASE_URL);
  }
}
