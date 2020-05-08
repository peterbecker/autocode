import {Component} from '@angular/core';
import {${entity.name}} from "./${entity.name?lower_case}.model";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@Component({
    selector: 'autocode-${entity.name?lower_case}-list',
    templateUrl: './${entity.name?lower_case}-list.component.html',
    styleUrls: ['./${entity.name?lower_case}-list.component.css']
})
export class ${entity.name}ListComponent {
    items: ${entity.name}[];

    constructor(service: ${entity.name}Service) {
        <#--
        We don't use a route resolver here, since it can't refresh the data in a controlled way -- onSameUrlNavigation
        can't be attached to a child module.
        -->
        service.getAll().subscribe( data => { this.items = data; } );
    }
}