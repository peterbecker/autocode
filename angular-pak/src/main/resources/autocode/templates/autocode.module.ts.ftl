import {NgModule} from '@angular/core';
<#list entities as entity>
import {${entity.name}Module} from "./${entity.name?lower_case}/${entity.name?lower_case}.module";
</#list>
import {AutocodeRoutingModule} from "./autocode-routing.module";

@NgModule({
    exports: [
<#list entities as entity>
        ${entity.name}Module,
</#list>
        AutocodeRoutingModule
    ]
})
export class AutocodeModule {
}
