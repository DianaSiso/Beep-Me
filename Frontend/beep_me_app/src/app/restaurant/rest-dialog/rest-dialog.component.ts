import { Component, Inject, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { analyzeAndValidateNgModules } from '@angular/compiler';
@Component({
  selector: 'app-rest-dialog',
  templateUrl: './rest-dialog.component.html',
  styleUrls: ['./rest-dialog.component.css']
})
export class RestDialogComponent2 implements OnInit {

    counter=10;
    constructor(
        private dialogRef: MatDialogRef<RestDialogComponent2>,@Inject(MAT_DIALOG_DATA) public data:any) {
        
        this.dialogRef = dialogRef;
    }

    ngOnInit() {
        
    }

}
