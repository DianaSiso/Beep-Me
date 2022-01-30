import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';

import { HttpClient,HttpClientJsonpModule } from '@angular/common/http';



@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {



  
  delayed:Number[]=[];
  ABCchartF=[];
  totalOrders:number[]=[];
  restaurantsDeliveredchart:Chart[]=[];
  avgTimeMap = new Map<string, string>();
  constructor(private httpClient:HttpClient){}
  
  fetchTotalOrders(){
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080//restaurant/total_orders').subscribe(res => {
      this.totalOrders.push(res.kfc);
      this.totalOrders.push(res.aguladoprego);
      this.totalOrders.push(res.akigrill);
      this.totalOrders.push(res.alicarius);-
      this.totalOrders.push(res.bicadaria);
      this.totalOrders.push(res.caffelato);
      this.totalOrders.push(res.caseiroebom);
      this.totalOrders.push(res.domfranguito);
      this.totalOrders.push(res.h3);
      this.totalOrders.push(res.hummy);
      this.totalOrders.push(res.italianrepublic);
      this.totalOrders.push(res.malguinhasepregos);
      this.totalOrders.push(res.maniapokebowls);
      this.totalOrders.push(res.mcdonalds);
      this.totalOrders.push(res.oita);
      this.totalOrders.push(res.paodivino);
      this.totalOrders.push(res.patronopizza);
      this.totalOrders.push(res.sical);
      this.totalOrders.push(res.sunbufe);
      this.totalOrders.push(res.tacobell);
      this.totalOrders.push(res.tasquinhadobacalhau);
      this.totalOrders.push(res.vitaminas);
      this.totalOrders.push(res.zedatripa);

      var sum = 0;
      sum = this.totalOrders.reduce((acc, cur) => acc + cur, 0);

      for(let i = 0; i < this.totalOrders.length; i++){
        this.totalOrders[i] = (this.totalOrders[i] / sum) * 100;
      }

      const ABCchartF = new Chart('ABCchartF', {
        type: 'bar',
        data: {
          labels: [
            ''
          ],
          datasets: [
            {
              data: [this.totalOrders[0]],
              label:'KFC',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[1]],
              label:'A gula do Prego',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[2]],
              label:'Aki Grill',
              backgroundColor: 'royalblue',
            },
            
            {
              data: [this.totalOrders[3]],
              label:'Alicarius',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[4]],
              label:'Bica da ria',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[5]],
              label:'Caffelato',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[6]],
              label:'Caseiro e Bom',
              backgroundColor: ' royalblue',
            },
            {
              data: [this.totalOrders[7]],
              label:'Dom Franguito',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[8]],
              label:'H3',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[9]],
              label:'Hummy',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[10]],
              label:'Italian Republic',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[11]],
              label:'Malguinhas e Pregos',
              backgroundColor: ' lightseagreen',
            },
            {
              data: [this.totalOrders[12]],
              label:'Mania Poke Bowls',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[13]],
              label:'MC Donalds',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[14]],
              label:'Oita',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[15]],
              label:'Pão Divino',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[16]],
              label:'Patrono Pizza',
              backgroundColor: ' royalblue',
            },
            {
              data: [this.totalOrders[17]],
              label:'Sical',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[18]],
              label:'Sun Bufê',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[19]],
              label:'Taco Bell',
              backgroundColor: 'lightseagreen',
            },
            {
              data: [this.totalOrders[20]],
              label:'Tasquinha do Bacalhau',
              backgroundColor: 'royalblue',
            },
            {
              data: [this.totalOrders[21]],
              label:'Vitaminas',
              backgroundColor: ' lightseagreen',
            },
            {
              data: [this.totalOrders[22]],
              label:'Zé da Tripa',
              backgroundColor: 'royalblue',
            },
          ],
        },
        options: {
          responsive:false,
          scales: {
            y: {
              beginAtZero: true,
              stacked: true
            },
            x: {
                stacked: true,
          },
        },
          plugins:{
            legend:{
              display:false,
            },
            title: {
              display: true,
              text: 'Restaurants contributions'
            },
          }
  
      }
      });
    })
  }


  fetchNotDelivered(){

    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080//restaurant/orders/notDelivered').subscribe(res=>
    {
      this.delayed.push(res.kfc);
      this.delayed.push(res.aguladoprego);
      this.delayed.push(res.akigrill);
      this.delayed.push(res.alicarius);
      this.delayed.push(res.bicadaria);
      this.delayed.push(res.caffelato);
      this.delayed.push(res.caseiroebom);
      this.delayed.push(res.domfranguito);
      this.delayed.push(res.h3);
      this.delayed.push(res.hummy);
      this.delayed.push(res.italianrepublic);
      this.delayed.push(res.malguinhasepregos);
      this.delayed.push(res.maniapokebowls);
      this.delayed.push(res.mcdonalds);
      this.delayed.push(res.oita);
      this.delayed.push(res.paodivino);
      this.delayed.push(res.patronopizza);
      this.delayed.push(res.sical);
      this.delayed.push(res.sunbufe);
      this.delayed.push(res.tacobell);
      this.delayed.push(res.tasquinhadobacalhau);
      this.delayed.push(res.vitaminas);
      this.delayed.push(res.zedatripa);
    });

    return this.delayed
  }

   fetchDelivered():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080//restaurant/orders/delivered').subscribe(res=>
    {
      this.restaurantsDeliveredchart.push(
        new Chart('myChart2', {
        type: 'bar',
        data: {
          labels: [
                'KFC',
                'aguladoprego',
                'akigrill',
                'alicarius',
                'bicadaria',
                'caffelato',
                'caseiroebom',
                'domfranguito',
                'h3',
                'hummy',
                'italianrepublic',
                'malguinhasepregos',
                'maniapokebowls',
                'mcdonalds',
                'oita',
                'paodivino',
                'patronopizza',
                'sical',
                'sunbufe',
                'tacobell',
                'tasquinhadobacalhau',
                'vitaminas',
                'zedatripa',
          ],
          datasets: [
            {
              label: 'Delivered',
              data: [
                res.kfc,
                res.aguladoprego,
                res.akigrill,
                res.alicarius,
                res.bicadaria,
                res.caffelato,
                res.caseiroebom,
                res.domfranguito,
                res.h3,
                res.hummy,
                res.italianrepublic,
                res.malguinhasepregos,
                res.maniapokebowls,
                res.mcdonalds,
                res.oita,
                res.paodivino,
                res.patronopizza,
                res.sical,
                res.sunbufe,
                res.tacobell,
                res.tasquinhadobacalhau,
                res.vitaminas,
                res.zedatripa,
              ],              backgroundColor: 'rgba(0, 255, 0, 0.5)',
            },
            {
              label: 'Not Delivered',
              data: this.fetchNotDelivered(),
              backgroundColor: 'rgba(255, 242, 0, 0.5)',
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
      }));

    });
  }

  fetchAvgTime():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/restaurant/orders/meanTime').subscribe(res=>
    {
      for (var value in res) {  
        this.avgTimeMap.set(value,res[value])  
     }



    const avgTime = new Chart('avgTime', {
     type: 'bar',
     data: {
       labels: Array.from(this.avgTimeMap.keys()),
       datasets: [
         {
           label:'Average wait time per restaurant (Minutes)',
           data:Array.from(this.avgTimeMap.values()),
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




  ngOnInit(): void {
    this.fetchTotalOrders();
    this.fetchDelivered();
    this.fetchAvgTime();
  }



}
