import {NgModule} from '@angular/core';
import {CommonModule} from "@angular/common";
import {${entity.name}Component} from "./${entity.name?lower_case}.component";
import {${entity.name}ListComponent} from "./${entity.name?lower_case}-list.component";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        ${entity.name}Component,
        ${entity.name}ListComponent
    ],
    providers: [
        ${entity.name}Service
    ]
})
export class ${entity.name}Module {
}
