import { Component, OnInit } from '@angular/core';
import { Task } from '../restaurant/task/task';
import { transferArrayItem, CdkDragDrop } from '@angular/cdk/drag-drop';
import { MatDialog,MatDialogConfig } from '@angular/material/dialog';
import { RestDialogComponent } from './rest-dialog/rest-dialog.component'; 
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurante2.component.html',
  styleUrls: ['./restaurante2.component.css']
})
export class Restaurante2Component implements OnInit {  
  
  title = 'beep-me-web-app';
  todo: Task[] = [];
  
  constructor(public dialog:MatDialog,private httpClient:HttpClient){}
  fetchData():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/orders/restaurant?rest_id=2').subscribe(response=>{console.log(response);this.todo=response;});
  }
  editTask(list: string, task:Task): void{}
  
  openDialog(task:Task) {
    console.log("opendialogfunc")
    let dialogRef = this.dialog.open(RestDialogComponent, {data : {name: task.title}});
    dialogRef.afterClosed().subscribe(result => {console.log(`Dialog result: ${result}`);//aqui vamos aumentar atraso e passar para o cliente
  });
}
  ngOnInit(): void {
    this.fetchData();
  }

}
