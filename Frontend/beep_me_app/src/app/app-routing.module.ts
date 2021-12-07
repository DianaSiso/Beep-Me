import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { TaskComponent } from './restaurant/task/task.component';
import { TaskDialogComponent } from './restaurant/task-dialog/task-dialog.component';
import { ChartsComponent } from './charts/charts.component';

const routes: Routes = [
  {path: '', component:LoginComponent},
  {path: 'restaurant-component', component:RestaurantComponent},
  {path: 'task-component', component:TaskComponent},
  {path: 'task-dialog-component', component:TaskDialogComponent},
  {path: 'charts-component', component:ChartsComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
