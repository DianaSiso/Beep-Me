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

  rest_id= localStorage.getItem('restID');


  constructor(public dialog:MatDialog,private httpClient:HttpClient){}
  fetchData():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/orders/restaurant?rest_id='+this.rest_id).subscribe(response=>{console.log(response);
    this.todo=[];
    for (var i=0; i<response.length;i++){
      if(response[i].state == 'ORDERED' || response[i].state == 'LATE'){
        this.todo.push(response[i]);
      }

    }
    });
  }
  editTask(list: string, task:Task): void{}
  
  openDialog(task:Task) {
    console.log("opendialogfunc")
    let dialogRef = this.dialog.open(RestDialogComponent, {data : {name: task.id}});
    dialogRef.afterClosed().subscribe(result => {console.log(`Dialog result: ${result}`);
    //aqui vamos aumentar atraso e passar para o cliente
    if (result !=0){
      this.httpClient.post<any>('http://deti-engsoft-02.ua.pt:8080/orders/delayed',{'order_id':task.id,'minutes':result}).subscribe(response=>{console.log(response)});
      console.log(result)
    }
  });
}
  ngOnInit(): void {
    this.fetchData();
    setInterval(()=>{this.fetchData();},5000);
  }

}
