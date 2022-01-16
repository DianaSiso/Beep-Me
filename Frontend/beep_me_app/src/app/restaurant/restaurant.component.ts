import { Component, OnInit } from '@angular/core';
import { Task } from './task/task';
import { transferArrayItem, CdkDragDrop } from '@angular/cdk/drag-drop';
import { MatDialog } from '@angular/material/dialog';
import { TaskDialogComponent, TaskDialogResult } from './task-dialog/task-dialog.component';
import { HttpClient } from '@angular/common/http';
import { RestDialogComponent2 } from './rest-dialog/rest-dialog.component'; 

import { identifierModuleUrl } from '@angular/compiler';
import { toBase64String } from '@angular/compiler/src/output/source_map';
@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {

  title = 'beep-me-web-app';
  todo: Task[] = [
  ];
  inProgress: Task[]=[];
  done: Task[]=[];
  constructor(private dialog:MatDialog,private httpClient:HttpClient){}
  fetchData():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/orders/restaurant?rest_id=2').subscribe(response=>{console.log(response);
    this.todo=[];
    this.inProgress=[];
    this.done=[];
    for (var i=0; i<response.length;i++){
      if(response[i].state == 'DELIVERED'){
        this.done.push(response[i]);
      }
      if(response[i].state == 'ORDERED' || response[i].state == 'LATE'){
        this.todo.push(response[i]);
      }
      if(response[i].state == 'READY'){
        this.inProgress.push(response[i]);
      }

    }
    });
  }
  newTask(): void{
    const dialogRef = this.dialog.open(TaskDialogComponent,{
      width:'270px',
      data:{
        task:{},
      },
    });
    dialogRef
    .afterClosed()
    .subscribe((result:TaskDialogResult) => this.todo.push(result.task));
  }
  editTask(list: string, task:Task): void{}
  drop(event: CdkDragDrop<Task[] | any>): void{
    if (event.previousContainer === event.container){
      return;
    }
    if (!event.container.data || !event.previousContainer.data){
      return;
    }
    
    transferArrayItem(
      event.previousContainer.data,
      event.container.data,
      event.previousIndex,
      event.currentIndex
    );
    //console.log("changed",event.container.data)
    var tempstate='DELIVERED';
    for(var i=0;i<event.container.data.length;i++){
      console.log("changed",event.container.data[i].id)
      if(event.container.data[i].state == 'ORDERED' || event.container.data[i].state == 'LATE'){
        tempstate='READY';
      }
      this.httpClient.post<any>('http://deti-engsoft-02.ua.pt:8080/orders/state',{'order_id':event.container.data[i].id,'state':tempstate}).subscribe(response=>{console.log(response)});
      
    }
  }
  openDialog(task:Task) {
    console.log("opendialogfunc")
    let dialogRef = this.dialog.open(RestDialogComponent2, {data : {name: task.id}});
    dialogRef.afterClosed().subscribe(result => {console.log(`Dialog result: ${result}`);
    if (result == "cancel"){
      console.log(result);
      //enviar cancelar com num do pedido
      this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/orders/cancel?order_id'+task.id).subscribe(response=>{console.log(response)});

    }
    
  });}

  ngOnInit(): void {
    this.fetchData();
    setInterval(()=>{this.fetchData();},5000);
  }

}
