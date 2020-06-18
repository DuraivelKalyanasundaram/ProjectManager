import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ProjectDTO } from 'src/app/model/ProjectDTO';
import { ProjectService } from 'src/app/services/project.service';
import { TaskDTO } from 'src/app/model/TaskDTO';
import { TaskService } from 'src/app/services/task.service';

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.scss']
})
export class ViewTaskComponent implements OnInit {

  project = '';
  searchProjectText = '';
  closeResult: string;
  selectedProject: ProjectDTO;
  projects: ProjectDTO[];
  tasks: TaskDTO[];

  constructor(private modalService: NgbModal,
              private taskService: TaskService,
              private projectService: ProjectService) { }

  ngOnInit(): void {
    this.loadData();
  }


  onSelectedProjectChange(project: ProjectDTO) {
    this.selectedProject = project;
  }


  openProjectSearch(content) {
    this.searchProjectText = '';
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      if (this.selectedProject) {
        this.project = this.selectedProject.name;
        this.loadTasks();
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  sortBy(key) {
    console.log('Sorting by ' + key);
    switch(key) {
      case 'startDate':
        this.tasks.sort((a,b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
        break;
      case 'endDate':
        this.tasks.sort((a,b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
        break;
      case 'priority':
        this.tasks.sort((a,b) => Number(a.priority) - Number(b.priority));
        break;
      case 'completed':
        this.tasks.sort((a,b) => a.status.toLowerCase().localeCompare(b.status.toLowerCase()));
        break;
    }
  }


  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  private loadTasks() {
    this.taskService.getTasksForProject(this.selectedProject.id).subscribe(data => {
      console.log('Tasks retrieved are ' + data);
      this.tasks = data;
    }, error => {
      console.error('Error ' + error);
    });
  }

  private loadData() {
    this.projectService.getProjects().subscribe(data => {
      this.projects = data;
    }, error => {
      console.error('Error ' + error);
    });
  }

}
