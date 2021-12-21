import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule, Routes } from '@angular/router';
import { ChoiceComponent } from '../choice/choice.component';
const routes: Routes = [
  { path: '', redirectTo: '/choice', pathMatch: 'full' },
  { path: 'choice', component: ChoiceComponent },

];
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  type="";
  pwd="";
  //username=(<HTMLInputElement>document.getElementById("user")).value;
  username:string="";
  constructor(private httpClient:HttpClient,public router: Router) {
   }
  verifyLogin():void{
    console.log("username"+this.username)
    this.httpClient.get<any>('http://deti-engsoft-02.ua.pt:8080/login?username='+this.username+'pwd='+this.pwd).subscribe(response=>{console.log(response);
    if(response.status=="OK"){this.router.navigate(['/choice'])}
    else{
      console.log("Nope")
    }
  });
  }
  ngOnInit(): void {
  }

}
