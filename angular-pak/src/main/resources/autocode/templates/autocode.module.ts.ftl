import {NgModule} from '@angular/core';
<#list entities as entity>
import {${entity.name}Module} from "./${entity.name?lower_case}/${entity.name?lower_case}.module";
</#list>

@NgModule({
    exports: [
<#list entities as entity>
        ${entity.name}Module,
</#list>
    ]
})
export class AutocodeModule {
}
