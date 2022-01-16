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
    ChoiceComponent
  ],
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
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
