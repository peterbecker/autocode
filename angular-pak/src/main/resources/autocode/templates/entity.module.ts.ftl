import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {${entity.name}Component} from "./${entity.name?lower_case}.component";
import {${entity.name}ListComponent} from "./${entity.name?lower_case}-list.component";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatListModule,
    ],
    declarations: [
        ${entity.name}Component,
        ${entity.name}ListComponent,
    ],
    providers: [
        ${entity.name}Service,
    ]
})
export class ${entity.name}Module {
}
