import {Component} from '@angular/core';

@Component({
    selector: 'app-${entity.name?lower_case}-list',
    templateUrl: './${entity.name?lower_case}-list.component.html',
    styleUrls: ['./${entity.name?lower_case}-list.component.css']
})
export class ${entity.name}ListComponent {
}