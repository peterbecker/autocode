import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
<#list entities as entity>
import {${entity.name}Component} from "./${entity.name?lower_case}/${entity.name?lower_case}.component";
import {${entity.name}Resolver} from "./${entity.name?lower_case}/${entity.name?lower_case}.resolver";
import {${entity.name}ListComponent} from "./${entity.name?lower_case}/${entity.name?lower_case}-list.component";
</#list>
import {CommonModule} from '@angular/common';
import {AutocodeNavigationComponent} from "./navigation.component";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';

const routes: Routes = [
<#list entities as entity>
    {
        path: '${entity.name?lower_case}',
        component: ${entity.name}ListComponent,
        children: [
            {
                path: '!new',
                component: ${entity.name}Component,
                resolve: {
                    item: ${entity.name}Resolver
                },
            },
            {
                path: ':id',
                component: ${entity.name}Component,
                resolve: {
                    item: ${entity.name}Resolver
                },
            },
        ]
    },
</#list>
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        BrowserAnimationsModule,
        LayoutModule,
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatIconModule,
        MatListModule
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
