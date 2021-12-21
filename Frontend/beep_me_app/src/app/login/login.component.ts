import { Component, Input, OnInit } from '@angular/core';
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
  pwd: string | undefined;
  username: string | undefined;
  
  //username=(<HTMLInputElement>document.getElementById("user")).value;
  constructor(private httpClient:HttpClient,public router: Router) {
   }
  user(event:any) {this.username = event.target.value;}
  pswd(event:any) {this.pwd = event.target.value;}

  verifyLogin():void{
    console.log("http://deti-engsoft-02.ua.pt:8080/login?username="+this.username+"&pwd="+this.pwd)
    this.httpClient.get<any>("http://deti-engsoft-02.ua.pt:8080/login?username="+this.username+"&pwd="+this.pwd).subscribe(response=>{console.log(response);
    if(response.status=="OK"){this.router.navigate(['/choice'])}
    else{
      console.log("Nope")
    }
  });
  }
  ngOnInit(): void {
  }

}
