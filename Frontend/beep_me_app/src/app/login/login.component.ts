import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule, Routes } from '@angular/router';
import { ChoiceComponent } from '../choice/choice.component';
import { ChartsComponent } from '../charts/charts.component';
const routes: Routes = [
  { path: '', redirectTo: '/choice', pathMatch: 'full' },
  { path: '', redirectTo: '/charts-component', pathMatch: 'full' },
  { path: 'choice', component: ChoiceComponent },
  { path: 'charts-component', component: ChartsComponent },

];
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  type="";
  pwd: string | undefined;
  username: string | undefined;
  state="";
  constructor(private httpClient:HttpClient,public router: Router) {
   }
  user(event:any) {this.username = event.target.value;}
  pswd(event:any) {this.pwd = event.target.value;}

  verifyLogin():void{

    this.httpClient.post<any>("http://deti-engsoft-02.ua.pt:8080/login",{username:this.username,password:this.pwd}).subscribe(response=>{console.log(response);
    if(response.status=="OK"){
      if(response.manager=="0"){
        //myGlobals.rest_id=response.rest_id;
        localStorage.setItem('restID', response.rest_id);
        console.log(localStorage.getItem('restID'));
        this.router.navigate(["/choice"])}
      else{
        this.router.navigate(["/charts-component"])
      }
    }
    else{
      console.log("Nope");
      this.state="Wrong";
    }
  });
  }
  ngOnInit(): void {
    this.state="";
    setInterval(()=>{this.state="";},5000);

  }

}
