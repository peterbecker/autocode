import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
<#list entities as entity>
import {${entity.name}Component} from "./${entity.name?lower_case}/${entity.name?lower_case}.component";
import {${entity.name}ListComponent} from "./${entity.name?lower_case}/${entity.name?lower_case}-list.component";
</#list>
import {AutocodeNavigationComponent} from "./navigation.component";

const routes: Routes = [
<#list entities as entity>
    {
        path: '${entity.name?lower_case}',
        component: ${entity.name}ListComponent
    },
    {
        path: '${entity.name?lower_case}/:id',
        component: ${entity.name}Component
    },
</#list>
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule,
        AutocodeNavigationComponent
    ],
    declarations: [
        AutocodeNavigationComponent
    ]
})
export class AutocodeRoutingModule {
}
