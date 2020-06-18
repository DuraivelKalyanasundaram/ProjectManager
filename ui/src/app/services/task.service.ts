import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Task } from '../model/Task';
import { TaskDTO } from '../model/TaskDTO';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private BASE_URL = 'http://localhost:9090/tasks';
  private httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  addTask(task: Task) {
    return this.httpClient.post(this.BASE_URL, task, this.httpOptions);
  }

  getTasks() {
    return this.httpClient.get<TaskDTO[]>(this.BASE_URL);
  }

  getTasksForProject(id: Number){
    return this.httpClient.get<TaskDTO[]>(this.BASE_URL + '/project/' + id);
  }

  updateTask(task: TaskDTO) {
    return this.httpClient.put<TaskDTO>(this.BASE_URL + '/' + task.id, task, this.httpOptions);
  }

}
