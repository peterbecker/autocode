import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
<#list entities as entity>
import {${entity.name}Module} from "./${entity.name?lower_case}/${entity.name?lower_case}.module";
</#list>
import {AutocodeRoutingModule} from "./autocode-routing.module";
import {HttpClientModule} from '@angular/common/http';

@NgModule({
    exports: [
<#list entities as entity>
        ${entity.name}Module,
</#list>
        AutocodeRoutingModule
    ],
    imports: [
        HttpClientModule
    ]
})
export class AutocodeModule {
}
