import { Component, OnInit } from '@angular/core';
import { Task } from './task/task';
import { transferArrayItem, CdkDragDrop } from '@angular/cdk/drag-drop';
import { MatDialog } from '@angular/material/dialog';
import { TaskDialogComponent, TaskDialogResult } from './task-dialog/task-dialog.component';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {

  title = 'beep-me-web-app';
  todo: Task[] = [
    {title: 'Pedido nº 1354',description:''},
    {title: 'Pedido nº 1364',description:'Atrasado'},
    {title: 'Pedido nº 1374',description:''}
  ];
  inProgress: Task[]=[];
  done: Task[]=[];
  constructor(private dialog:MatDialog){}
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
  }

  ngOnInit(): void {
  }

}
