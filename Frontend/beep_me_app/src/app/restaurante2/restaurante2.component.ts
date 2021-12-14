import { Component, OnInit } from '@angular/core';
import { Task } from '../restaurant/task/task';
import { transferArrayItem, CdkDragDrop } from '@angular/cdk/drag-drop';
import { MatDialog,MatDialogConfig } from '@angular/material/dialog';
import { RestDialogComponent } from './rest-dialog/rest-dialog.component';
@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurante2.component.html',
  styleUrls: ['./restaurante2.component.css']
})
export class Restaurante2Component implements OnInit {  
  
  title = 'beep-me-web-app';
  todo: Task[] = [
    {title: 'Pedido nº 1354',description:''},
    {title: 'Pedido nº 1364',description:'Atrasado'},
    {title: 'Pedido nº 1374',description:''}
  ];
  inProgress: Task[]=[];
  done: Task[]=[];
  constructor(private dialog:MatDialog){}
 
  editTask(list: string, task:Task): void{}
  
  openDialog() {
    console.log("opendialogfunc")
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.position = {
      'top': '0',
      'left': '0'
  };
    dialogConfig.data = {
        title: 'Angular For Beginners'
    };

    this.dialog.open(RestDialogComponent, dialogConfig);
    
    const dialogRef = this.dialog.open(RestDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
        data => console.log("Dialog output:", data)
    );    
}
  ngOnInit(): void {
  }

}
