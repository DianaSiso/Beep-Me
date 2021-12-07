import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    const myChart = new Chart('myChart', {
      type: 'bar',
      data: {
        labels: [
          'KFC',
          "McDonald's",
          'Taco Bell',
          'H3',
          'Vitaminas',
          'Ali Kebab',
        ],
        datasets: [
          {
            label: 'Revenue This Year',
            data: [25000, 58000, 28000, 9000, 10000, 18000],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgb(75, 192, 192)',
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });

    const myChart2 = new Chart('myChart2', {
      type: 'bar',
      data: {
        labels: [
          'KFC',
          "McDonald's",
          'Taco Bell',
          'H3',
          'Vitaminas',
          'Ali Kebab',
        ],
        datasets: [
          {
            label: 'Delivered',
            data: [400,700,480,150,200,300],
            backgroundColor: 'rgba(0, 255, 0, 0.2)',
          },
          {
            label: 'Not Delivered',
            data: [30,45,15,10,20,25],
            backgroundColor: 'rgba(255, 242, 0, 0.8)',
          },
          {
            label: 'Rejected',
            data: [2,1,4,5,1,3],
            backgroundColor: 'rgba(255, 0, 0, 0.8)',
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
  }



}
