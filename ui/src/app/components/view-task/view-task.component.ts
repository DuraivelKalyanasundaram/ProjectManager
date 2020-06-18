import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ProjectDTO } from 'src/app/model/ProjectDTO';
import { ProjectService } from 'src/app/services/project.service';
import { TaskDTO } from 'src/app/model/TaskDTO';
import { TaskService } from 'src/app/services/task.service';
import { ParentTaskDTO } from 'src/app/model/ParentTaskDTO';
import { ParenttaskService } from 'src/app/services/parenttask.service';
import { NgForm } from '@angular/forms';

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
  taskUpdatedSuccessfully = false;
  taskUpdatedMessage = false;
  taskName = '';
  parentTask = false;
  priorityValue = 0;
  parentTaskName = '';
  startDateSelected;
  endDateSelected;
  userName = '';
  searchParentTaskText = '';
  taskBeingUpdated: TaskDTO;
  selectedParentTask: ParentTaskDTO;
  parentTasks: ParentTaskDTO[];
  @ViewChild('endDate') endDate: ElementRef;

  constructor(private modalService: NgbModal,
              private taskService: TaskService,
              private projectService: ProjectService,
              private parentTaskService: ParenttaskService) { }

  ngOnInit(): void {
    this.loadData();
  }


  onSelectedProjectChange(project: ProjectDTO) {
    this.selectedProject = project;
  }

  startDateSelect() {
    const newEndDate = this.getTomorrowFromDate(new Date(this.startDateSelected));
    this.endDateSelected = newEndDate;
    this.endDate.nativeElement.setAttribute('min', newEndDate);
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

  openEditTask(content, task:TaskDTO) {
    this.taskBeingUpdated = task;
    this.taskName = task.name;
    this.priorityValue = Number(task.project);
    this.parentTask = false;
    if(task.parentTask) {
      this.parentTaskName = task.parentTask.name;
    } else {
      this.parentTaskName = '';
    }
    this.startDateSelected = task.startDate;
    this.endDateSelected =task.endDate;
    this.userName = task.user.firstName + ' ' + task.user.lastName;
    this.loadParentTasks();
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      this.loadTasks();
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  useraction(updateTaskForm: NgForm) {
    this.taskBeingUpdated.name = this.taskName;
    this.taskBeingUpdated.priority = this.priorityValue.toString();
    this.taskBeingUpdated.startDate = this.startDateSelected;
    this.taskBeingUpdated.endDate = this.endDateSelected;
    console.log('New updated task ' + JSON.stringify(this.taskBeingUpdated));
    this.taskService.updateTask(this.taskBeingUpdated).subscribe(data => {
      this.taskUpdatedMessage = true;
      console.log('Task successfully updated');
      setTimeout(()=> {
        this.taskUpdatedMessage = true;
      }, 5000);
    })
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
  onSelectedParentTaskChange(parentTask: ParentTaskDTO){
    this.selectedParentTask = parentTask;
  }

  openParentSearch(content) {
    this.searchParentTaskText = '';
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with ${result}`;
      if(this.selectedParentTask) {
        this.parentTaskName = this.selectedParentTask.name;
        this.taskBeingUpdated.parentTask = this.selectedParentTask;
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  setComplete(task: TaskDTO) {
    task.status = 'COMPLETED';
    this.taskService.updateTask(task).subscribe(data => {
      this.taskUpdatedSuccessfully = true;
      this.loadTasks();
      setTimeout(()=> {
        this.taskUpdatedSuccessfully = false;
      }, 5000);
    }, error => {
      console.error('Error ' + error);
    })
  }

  private loadParentTasks() {
    this.parentTaskService.getParentTasks().subscribe(data => {
      this.parentTasks = data;
    }, error => {
      console.error('Error ' + error);
    });
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

  private getTomorrowFromDate(dat: Date) {
    const dt = new Date(dat.getTime() + 24 * 60 * 60 * 1000);
    let d = dt.getDate();
    let mon = dt.getMonth() + 1;
    let year = dt.getFullYear();
    let month = '';
    let day = '';
    if (mon < 10) {
      month = "0" + mon;
    } else {
      month = mon.toString();
    }
    if (d < 10) {
      day = "0" + d;
    } else {
      day = d.toString();
    }
    return year + "-" + month + "-" + day;
  }

}
