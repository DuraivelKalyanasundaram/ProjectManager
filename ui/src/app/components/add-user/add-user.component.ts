import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  firstName: string;
  lastName: string;
  empId:string;
  userAdded = false;
  users : User[];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  addNewUser(newUserForm: NgForm) {
    console.log('New user button clicked on form' + newUserForm );
    this.userService.adduser(new User(this.firstName, this.lastName, this.empId)).subscribe(data => {
      console.log ('User added ' + data);
      this.userAdded = true;
      setTimeout(() => {
        this.userAdded = false
      }, 5000);
      this.getUsers();
    }, error => {
      console.log(" error " + error)
    });
    newUserForm.reset();
  }

  getUsers() {
    this.userService.getUsers().subscribe(data => {
      console.log('Recieved data ' + data);
      this.users = data as User[];
      console.log(this.users);
    })
  }
}
