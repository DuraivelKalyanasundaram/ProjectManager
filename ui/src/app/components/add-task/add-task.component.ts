import { Component, OnInit } from '@angular/core';
import { ProjectDTO } from 'src/app/model/ProjectDTO';
import { ProjectService } from 'src/app/services/project.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.scss']
})
export class AddTaskComponent implements OnInit {

  project = '';
  searchProjectText = '';
  projects: ProjectDTO[];
  selectedProject : ProjectDTO;
  closeResult: string;
  taskName = '';
  parentTask = '';

  constructor(private projectService: ProjectService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.pageInitialize();
  }

  onSelectedProjectChange(project: ProjectDTO) {
    this.selectedProject = project;
  }

  parentTaskSelect() {
    //Disable the other fields
  }

  private pageInitialize() {
    this.project = '';
    this.searchProjectText = '';
    this.taskName = '';
    this.parentTask = '';
    this.projectService.getProjects().subscribe(data => {
      this.projects = data;
    }, error => {
      console.error('Error ' + error);
    })
  }

  open(content) {
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

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
