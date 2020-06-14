import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/user.service';
import { UserDTO } from 'src/app/model/UserDTO';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Project } from 'src/app/model/Project';
import { ProjectService } from 'src/app/services/project.service';
import { ProjectDTO } from 'src/app/model/ProjectDTO';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.scss']
})
export class AddProjectComponent implements OnInit, AfterViewInit {

  projectTitle = '';
  manager = '';
  buttonAction = 'Add';
  startEndDateChecked = false;
  @ViewChild('startDate') startDate: ElementRef;
  @ViewChild('endDate') endDate: ElementRef;
  priorityValue = 0;
  startDateSelected;
  endDateSelected;
  closeResult: string;
  users: UserDTO[]
  selectedManager: UserDTO;
  updateMode = false;
  searchText = '';
  projectAdded = false;
  projectUpdated = false;
  projectDeleted = false;
  projects: ProjectDTO[];
  selectedProject: ProjectDTO;

  constructor(private modalService: NgbModal, 
              private userService: UserService,
              private projectService: ProjectService) { }

  ngOnInit(): void {
    this.getUsers();
    this.getProjects();
  }

  ngAfterViewInit() {
    const today = this.getToday();
    const tomorrow = this.getTomorrow();
    this.startDate.nativeElement.setAttribute('value', today);
    this.startDate.nativeElement.setAttribute('min', today);
    this.endDate.nativeElement.setAttribute('value', tomorrow);
  }

  startDateEndDateSelect() {
    if(this.startEndDateChecked) {
      const today = this.getToday();
      const tomorrow = this.getTomorrow();
      this.startDateSelected = today;
      this.endDateSelected = tomorrow;
      this.endDate.nativeElement.setAttribute('min', tomorrow);
    }
  }

  startDateSelect() {
    const newEndDate = this.getTomorrowFromDate(new Date(this.startDateSelected));
    this.endDateSelected = newEndDate;
    this.endDate.nativeElement.setAttribute('min', newEndDate);
  }

  getUsers() {
    this.userService.getUsers().subscribe(data => {
      this.users = data as UserDTO[];
    })
  }

  getProjects() {
    this.projectService.getProjects().subscribe(data => {
      this.projects = data as ProjectDTO[];
    }, error => {
      console.error('Error ' + error);
    })
  }

  onSelectedManagerChange(user: UserDTO) {
    this.selectedManager = user;
  }

  useraction(projectForm: NgForm) {
    if (this.updateMode) {
      this.selectedProject.name = this.projectTitle;
      this.selectedProject.startDate = this.startDateSelected;
      this.selectedProject.endDate = this.endDateSelected;
      this.selectedProject.priority = this.priorityValue.toString();
      this.selectedProject.manager = this.selectedManager;
      this.projectService.updateProject(this.selectedProject).subscribe(data => {
        this.projectUpdated = true;
        setTimeout(() => {
          this.projectUpdated = false;
        }, 5000);
        this.getProjects();
      }, error => {
        console.error('Error ' + error);
      })
    } else {
      const project = new Project(this.projectTitle, this.startDateSelected, this.endDateSelected, this.priorityValue.toString(), this.selectedManager);  
      this.projectService.addProject(project).subscribe(data => {
        console.log ('Project added ' + data);
        this.projectAdded = true;
        setTimeout(() => {
          this.projectAdded = false
        }, 5000);
        this.getProjects();
      }, error => {
        console.error('Error ' + error);
      })
    }
    this.buttonAction = 'Add';
    projectForm.reset();
  }

  editProject(project: ProjectDTO) {
    console.log('Editing project ' + JSON.stringify(project));
    this.updateMode = true;
    this.projectTitle = project.name;
    this.startEndDateChecked = true;
    this.startDateSelected = project.startDate.slice(0, 10);
    this.endDateSelected = project.endDate.slice(0,10);
    this.priorityValue = Number(project.priority);
    this.selectedManager = project.manager;
    this.manager = this.selectedManager.firstName + ' ' + this.selectedManager.lastName;
    this.selectedProject = project;
    this.buttonAction = 'Update';
  }

  open(content) {
    this.searchText = '';
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      if (this.selectedManager) {
        this.manager = this.selectedManager.firstName + ' ' + this.selectedManager.lastName;
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });

  }

  sortBy(event) {
    console.log ('Sorting on ' + event);
    switch(event) {
      case 'startDate':
        this.projects.sort((a,b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
        break;
      case 'endDate':
        this.projects.sort((a,b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
        break;
      case 'priority': 
      this.projects.sort((a,b) => Number(a.priority) - Number(b.priority));
        break;
      case 'completed':
        break;
    }
  }

  suspendProject(project: ProjectDTO) {

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
