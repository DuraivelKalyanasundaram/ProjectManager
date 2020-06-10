import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/model/User';
import { UserDTO } from 'src/app/model/UserDTO';

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
  userUpdated = false;
  userDeleted = false;
  updateMode = false;
  editedUser: UserDTO;
  users : UserDTO[];
  buttonAction = 'Add';
  searchText = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  useraction(newUserForm: NgForm) {
    console.log('User Action button clicked on form' + newUserForm );
    if (this.updateMode) {
      this.updateUser();
    } else {
      this.addUser(new User(this.firstName, this.lastName, this.empId));
    }
    this.initializeForm(newUserForm)
  }


  initializeForm(newUserForm: NgForm) {
    this.updateMode = false;
    this.buttonAction = 'Add';
    newUserForm.reset();
  }

  addUser(user: User) {
    this.userService.adduser(user).subscribe(data => {
      console.log ('User added ' + data);
      this.userAdded = true;
      setTimeout(() => {
        this.userAdded = false
      }, 5000);
      this.getUsers();
    }, error => {
      console.log(" error " + error)
    });
  }

  updateUser() {
    this.editedUser.firstName = this.firstName;
    this.editedUser.lastName = this.lastName;
    this.editedUser.employeeId = this.empId;
    this.userService.updateUser(this.editedUser).subscribe(data => {
      console.log ('User has been successfully updated');
      this.userUpdated = true;
      setTimeout(() => {
        this.userUpdated = false;
      }, 5000);
      this.getUsers();
    }, error => {
      console.log("error " + error);
    })
  }

  getUsers() {
    this.userService.getUsers().subscribe(data => {
      console.log('Recieved data ' + data);
      this.users = data as UserDTO[];
      console.log(this.users);
    })
  }

  editUser(user: UserDTO) {
    console.log ('edit user clicked - ' + user.id);
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.empId = user.employeeId;
    this.updateMode = true;
    this.buttonAction = 'Update'
    this.editedUser = user;
  }

  deleteUser(user: UserDTO) {
    this.userService.deleteUser(user).subscribe(data => {
      console.log ('User deleted ' + data);
      this.userDeleted = true;
      setTimeout(() => {
        this.userDeleted = false
      }, 5000);
      this.getUsers();
    }, error => {
      console.log (' Error ' + error)
    })
  }

  sortBy(event) {
    console.log ('Sort on ' + event);
    switch(event) {
      case 'firstname':
        this.users.sort((a,b) => a.firstName.localeCompare(b.firstName));
        break;
      case 'lastname':
        this.users.sort((a,b) => a.lastName.localeCompare(b.lastName));
        break;
      case 'empId': 
      this.users.sort((a,b) => a.employeeId.localeCompare(b.employeeId));
        break;
    }
  }
}
