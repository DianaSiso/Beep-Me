import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { TaskComponent } from './restaurant/task/task.component';
import { TaskDialogComponent } from './restaurant/task-dialog/task-dialog.component';
import { ChartsComponent } from './charts/charts.component'
import { Restaurante2Component} from './restaurante2/restaurante2.component';
import { ChoiceComponent } from './choice/choice.component';
import { RestaurantsComponent } from './restaurants';
import { H3Component } from './restaurants/H3';
import { KFCComponent } from './restaurants/KFC';
import { MCDComponent } from './restaurants/MCD';
import { AGUComponent } from './restaurants/AGU';
import { AKIComponent } from './restaurants/AKI';
import { TACComponent } from './restaurants/TAC';
import { VITComponent } from './restaurants/VIT';
import { ALIComponent } from './restaurants/ALI';
import { BICComponent } from './restaurants/BIC';
import { DOMComponent } from './restaurants/DOM';
import { CAFComponent } from './restaurants/CAF';
import { CASComponent } from './restaurants/CAS';
import { HUMComponent } from './restaurants/HUM';
import { OITComponent } from './restaurants/OIT';
import { PAOComponent } from './restaurants/PAO';
import { PATComponent } from './restaurants/PAT';
import { SICComponent } from './restaurants/SIC';
import { SUNComponent } from './restaurants/SUN';
import { ZEDComponent } from './restaurants/ZED';

const routes: Routes = [
  {path: '', component:LoginComponent},
  {path: 'restaurant-component', component:RestaurantComponent},
  {path: 'kitchen-component', component:Restaurante2Component},
  {path: 'task-component', component:TaskComponent},
  {path: 'task-dialog-component', component:TaskDialogComponent},
  {path: 'charts-component', component:ChartsComponent},

  {path: 'choice', component:ChoiceComponent},
  {path:'charts-component',component:ChartsComponent},
  {path:'restaurants-component',component:RestaurantsComponent},
  {path:'H3Component',component:H3Component},
  {path:'KFCComponent',component:KFCComponent},
  {path:'MCDComponent',component:MCDComponent},
  {path:'AGUComponent',component:AGUComponent},
  {path:'AKIComponent',component:AKIComponent},
  {path:'TACComponent',component:TACComponent},
  {path:'VITComponent',component:VITComponent},
  {path:'ALIComponent',component:ALIComponent},
  {path:'BICComponent',component:BICComponent},
  {path:'DOMComponent',component:DOMComponent},
  {path:'CAFComponent',component:CAFComponent},
  {path:'CASComponent',component:CASComponent},
  {path:'HUMComponent',component:HUMComponent},
  {path:'OITComponent',component:OITComponent},
  {path:'PAOComponent',component:PAOComponent},
  {path:'PATComponent',component:PATComponent},
  {path:'SICComponent',component:SICComponent},
  {path:'SUNComponent',component:SUNComponent},
  {path:'ZEDComponent',component:ZEDComponent},
  
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
