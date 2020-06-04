import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddProjectComponent } from './components/add-project/add-project.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { AddTaskComponent } from './components/add-task/add-task.component';
import { ViewTaskComponent } from './components/view-task/view-task.component';


const routes: Routes = [
  {path:'add-project', component: AddProjectComponent},
  {path:'add-user', component: AddUserComponent},
  {path:'add-task', component: AddTaskComponent},
  {path:'view-task', component: ViewTaskComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
