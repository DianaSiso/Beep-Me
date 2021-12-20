import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  successfull=false;
  type="";
  constructor(private httpClient:HttpClient) { }
  verifyLogin():void{
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/login').subscribe(response=>{console.log(response);if(response.status=="OK"){this.successfull=true;this.type=response.type}});
  }
  ngOnInit(): void {
  }

}
