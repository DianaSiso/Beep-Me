import { NgModule } from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import { Restaurante2Component } from '../restaurante2.component';
import { RestDialogComponent } from '../rest-dialog/rest-dialog.component';

@NgModule({
    declarations: [
        RestDialogComponent
    ],
    imports: [
        MatDialogModule
    ],
    providers: [
    ],
    bootstrap: [Restaurante2Component],
    entryComponents: [RestDialogComponent]
})

export class Restaurante2Module { }
