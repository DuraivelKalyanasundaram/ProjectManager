import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  firstName: string;
  lastName: string;
  empId:string;

  constructor() { }

  ngOnInit(): void {
  }

  addNewUser(newUserForm: NgForm) {
    console.log('New user button clicked on form' + newUserForm );
    console.log(this.firstName);
    console.log(this.lastName);
    console.log(this.empId);
    newUserForm.reset();
  }
}
