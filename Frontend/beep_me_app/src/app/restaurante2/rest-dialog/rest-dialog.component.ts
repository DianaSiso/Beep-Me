import { Component, Inject, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-rest-dialog',
  templateUrl: './rest-dialog.component.html',
  styleUrls: ['./rest-dialog.component.css']
})
export class RestDialogComponent implements OnInit {

    
    description:string;

    constructor(
        
        private form: FormGroup,
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<RestDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data:{title:string}) {

        this.description = data.title;
    }

    ngOnInit() {
        this.form = this.fb.group({
            description: [this.description, []]
        });
    }

    save() {
        this.dialogRef.close(this.form.value);
    }

    close() {
        this.dialogRef.close();
    }

}
