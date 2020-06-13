import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Project } from '../model/Project';
import { ProjectDTO } from '../model/ProjectDTO';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private BASE_URL = 'http://localhost:9090/projects';
  private httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  addProject(project: Project) {
    return this.httpClient.post(this.BASE_URL, JSON.stringify(project), this.httpOptions);
  }

  getProjects() {
    return this.httpClient.get<ProjectDTO[]>(this.BASE_URL);
  }

  updateProject(project: ProjectDTO) {
    return this.httpClient.put(this.BASE_URL + '/' + project.id, JSON.stringify(project), this.httpOptions);
  }
}
