import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { HttpClient,HttpClientJsonpModule } from '@angular/common/http';

@Component({
  templateUrl: './PAT.component.html',
  styleUrls: ['./PAT.component.css']
})


export class PATComponent implements OnInit {
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
        
      for (var value in res.patronopizza) {  
         this.map1.set(value,res.patronopizza[value])  
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


   array1 = {'KFC':2,'H3':1};

   
  fetchTest():void{ //Mudar rest_id //
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080//orders/restaurant?rest_id=36').subscribe(res=>
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

