import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { HttpClient,HttpClientJsonpModule } from '@angular/common/http';

@Component({
  templateUrl: './CAF.component.html',
  styleUrls: ['./CAF.component.css']
})


export class CAFComponent implements OnInit {
  months = ['Jan','Feb','Mar','Apr','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

  delayed:Number[]=[];
 
  totalOrders:number[]=[];
  restaurantOrders:Chart[]=[];
  pedidosStamp = new Map();
  statesMap = new Map();
  map1 = new Map<string, string>();
  constructor(private httpClient:HttpClient){}


  
  fetchRestStamps():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/restaurant/orders/perhour').subscribe(res=>
    {
        
      for (var value in res.caffelato) {  
         this.map1.set(value,res.caffelato[value])  
      }


     const restaurantMonth = new Chart('restaurantMonth', {
      type: 'bar',
      data: {
        labels: Array.from(this.map1.keys()),
        datasets: [
          {
            label:'Number of total orders per hour',
            data:Array.from(this.map1.values()),
            backgroundColor: 'rgba(0, 255, 0, 0.3)',
          },
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
    });
  }

   
  fetchTest():void{ //Mudar rest_id //
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080//orders/restaurant?rest_id=24').subscribe(res=>
    {
      for(let i= 0; i < res.length; i++){
        var temp = res[i].state;
        if(this.statesMap.has(temp)){ // +1
            this.statesMap.set(temp, this.statesMap.get(temp) +1);
        }
        else{ // = 1
          this.statesMap.set(temp,1);
        }   
    }

    const restaurantStates = new Chart('restaurantStates', {
      type: 'bar',
      data: {
        labels: Array.from(this.statesMap.keys()),
        datasets: [
          {
            label:'Current Orders States',
            data:Array.from(this.statesMap.values()),
            backgroundColor: 'rgba(0, 50, 200, 0.3)',
          },
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });

  });
}



  ngOnInit(): void {
    this.fetchRestStamps();
    this.fetchTest();
    //setInterval(()=>{this.fetchDelivered();},10000);




  } 
}

