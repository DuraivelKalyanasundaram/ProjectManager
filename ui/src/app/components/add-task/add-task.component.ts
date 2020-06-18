import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { ProjectDTO } from 'src/app/model/ProjectDTO';
import { ProjectService } from 'src/app/services/project.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { UserDTO } from 'src/app/model/UserDTO';
import { NgForm } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { ParenttaskService } from 'src/app/services/parenttask.service';
import { ParentTaskDTO } from 'src/app/model/ParentTaskDTO';
import { ParentTask } from 'src/app/model/ParentTask';
import { TaskService } from 'src/app/services/task.service';
import { Task } from 'src/app/model/Task';
import { TaskDTO } from 'src/app/model/TaskDTO';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.scss']
})
export class AddTaskComponent implements OnInit, AfterViewInit {

  project = '';
  searchProjectText = '';
  searchUserText = '';
  searchParentTaskText = '';
  projects: ProjectDTO[];
  selectedProject : ProjectDTO;
  closeResult: string;
  taskName = '';
  parentTask = false;
  priorityValue = 0;
  parentTaskName = '';
  userName = '';
  startDateSelected
  endDateSelected
  users: UserDTO[];
  selectedUser: UserDTO;
  parentTasks: ParentTaskDTO[];
  selectedParentTask: ParentTaskDTO;
  buttonAction = 'Add';
  parentTaskAddedSuccessfully = false;
  taskAddedSuccessfully = false;

  @ViewChild('startDate') startDate: ElementRef;
  @ViewChild('endDate') endDate: ElementRef;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private parentTaskService: ParenttaskService,
              private taskService: TaskService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.pageInitialize();
  }

  ngAfterViewInit() {
    this.initializeDateFields();
  }

  onSelectedProjectChange(project: ProjectDTO) {
    this.selectedProject = project;
  }

  onSelectedUserChange(user: UserDTO) {
    this.selectedUser = user;
  }

  onSelectedParentTaskChange(parentTask: ParentTaskDTO){
    this.selectedParentTask = parentTask;
  }

  parentTaskToggle() {
    if (!this.parentTask) {
      const today = this.getToday();
      const tomorrow = this.getTomorrow();
      this.startDateSelected = today;
      this.endDateSelected = tomorrow;
    }
  }

  useraction(taskForm: NgForm) {
    if(this.parentTask) {
      this.parentTaskService.addParentTask(new ParentTask(this.taskName)).subscribe(data =>{
        this.parentTaskAddedSuccessfully = true;
        this.loadParentTasks();
        setTimeout(() => {
          this.parentTaskAddedSuccessfully = false;
        },5000);
      });
    } else {
      const task = new Task(this.taskName, this.selectedParentTask, 
                            this.startDateSelected, this.endDateSelected,
                            this.priorityValue.toString(), "NOT_STARTED",
                            this.selectedUser, this.selectedProject);
      this.taskService.addTask(task).subscribe(data => {
        this.taskAddedSuccessfully = true;
        this.selectedProject = null;
        setTimeout(()=> {
          this.taskAddedSuccessfully = false;
        }, 5000);
      })
    }
    this.buttonAction = 'Add';
    taskForm.reset();
  }

  private initializeDateFields() {
    const today = this.getToday();
    const tomorrow = this.getTomorrow();
    this.startDateSelected = today;
    this.endDateSelected = tomorrow;
    this.startDate.nativeElement.setAttribute('value', today);
    this.startDate.nativeElement.setAttribute('min', today);
    this.endDate.nativeElement.setAttribute('value', tomorrow);
    this.endDate.nativeElement.setAttribute('min', tomorrow);
  }

  private pageInitialize() {
    this.project = '';
    this.searchProjectText = '';
    this.searchUserText = '';
    this.searchParentTaskText = '';
    this.taskName = '';
    this.parentTask = false;
    this.priorityValue = 0;
    this.parentTaskName = '';
    this.userName = '';
    this.buttonAction = 'Add';
    this.taskAddedSuccessfully = false;
    this.parentTaskAddedSuccessfully = false;
    this.selectedParentTask = null;
    this.projectService.getProjects().subscribe(data => {
      this.projects = data;
    }, error => {
      console.error('Error ' + error);
    });
    this.userService.getUsers().subscribe(data => {
      this.users = data as UserDTO[];
    }, error => {
      console.error('Error ' + error);
    });
    this.loadParentTasks();
  }

  setComplete(task: TaskDTO) {

  }

  openProjectSearch(content) {
    this.searchProjectText = '';
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      if (this.selectedProject) {
        this.project = this.selectedProject.name;
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  openUserSearch(content) {
    this.searchUserText = '';
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with ${result}`;
      if(this.selectedUser) {
        this.userName = this.selectedUser.firstName + ' ' + this.selectedUser.lastName;
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  openParentSearch(content) {
    this.searchParentTaskText = '';
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with ${result}`;
      if(this.selectedParentTask) {
        this.parentTaskName = this.selectedParentTask.name;
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  startDateSelect() {
    const newEndDate = this.getTomorrowFromDate(new Date(this.startDateSelected));
    this.endDateSelected = newEndDate;
    this.endDate.nativeElement.setAttribute('min', newEndDate);
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

  private loadParentTasks() {
    this.parentTaskService.getParentTasks().subscribe(data => {
      this.parentTasks = data;
      console.log('Parent Tasks '+ this.parentTasks);
    }, error => {
      console.error('Error ' + error);
    });
  }

  private getToday() {
    const dt = new Date();
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

  private getTomorrow() {
    const dt = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
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
