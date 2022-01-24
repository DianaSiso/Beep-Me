import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { TaskComponent } from './restaurant/task/task.component';
import { TaskDialogComponent } from './restaurant/task-dialog/task-dialog.component';
import { Restaurante2Component } from './restaurante2/restaurante2.component';
import { RestDialogComponent } from './restaurante2/rest-dialog/rest-dialog.component';
import { RestDialogComponent2 } from './restaurant/rest-dialog/rest-dialog.component';
import { HttpClientModule } from '@angular/common/http';
import { ChoiceComponent } from './choice/choice.component';
import { ChartsComponent } from './charts/charts.component';
import { RouterModule } from '@angular/router'
import { RestaurantsComponent } from './restaurants';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MCDComponent } from './restaurants/MCD';
import { AGUComponent } from './restaurants/AGU';
import { AKIComponent } from './restaurants/AKI';
import { ALIComponent } from './restaurants/ALI';
import { BICComponent } from './restaurants/BIC';
import { CAFComponent } from './restaurants/CAF';
import { CASComponent } from './restaurants/CAS';
import { DOMComponent } from './restaurants/DOM';
import { H3Component } from './restaurants/H3';
import { HUMComponent } from './restaurants/HUM';
import { KFCComponent } from './restaurants/KFC';
import { OITComponent } from './restaurants/OIT';
import { PAOComponent } from './restaurants/PAO';
import { PATComponent } from './restaurants/PAT';
import { SICComponent } from './restaurants/SIC';
import { SUNComponent } from './restaurants/SUN';
import { TACComponent } from './restaurants/TAC';
import { VITComponent } from './restaurants/VIT';
import { ZEDComponent } from './restaurants/ZED';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RestaurantComponent,
    TaskComponent,
    TaskDialogComponent,
    Restaurante2Component,
    RestDialogComponent,
    RestDialogComponent2,
    ChoiceComponent,
    ChartsComponent,
    RestaurantsComponent,
    AGUComponent,
    AKIComponent,
    ALIComponent,
    BICComponent,
    CAFComponent,
    CASComponent,
    DOMComponent,
    H3Component,
    HUMComponent,
    KFCComponent,
    MCDComponent,
    OITComponent,
    PAOComponent,
    PATComponent,
    SICComponent,
    SUNComponent,
    TACComponent,
    VITComponent,
    ZEDComponent

  ],
  exports: [RestaurantsComponent, AGUComponent,
    AKIComponent,
    ALIComponent,
    BICComponent,
    CAFComponent,
    CASComponent,
    DOMComponent,
    H3Component,
    HUMComponent,
    KFCComponent,
    MCDComponent,
    OITComponent,
    PAOComponent,
    PATComponent,
    SICComponent,
    SUNComponent,
    TACComponent,
    VITComponent,
    ZEDComponent],
  entryComponents: [RestDialogComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    FormsModule,
    MatDialogModule,
    DragDropModule,
    HttpClientModule,
    RouterModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
