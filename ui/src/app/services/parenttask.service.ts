import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { ParentTask } from '../model/ParentTask';
import { ParentTaskDTO } from '../model/ParentTaskDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParenttaskService {

  private BASE_URL = 'http://localhost:9090/parenttasks';
  private httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  addParentTask(parentTask: ParentTask) {
    return this.httpClient.post(this.BASE_URL, JSON.stringify(parentTask), this.httpOptions);
  }

  getParentTasks() {
    return this.httpClient.get<ParentTaskDTO[]>(this.BASE_URL);
  }
}
