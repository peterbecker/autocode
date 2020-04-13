import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {${entity.name}} from "./${entity.name?lower_case}.model";

@Component({
    selector: 'autocode-${entity.name?lower_case}-list',
    templateUrl: './${entity.name?lower_case}-list.component.html',
    styleUrls: ['./${entity.name?lower_case}-list.component.css']
})
export class ${entity.name}ListComponent {
    items: ${entity.name}[];

    constructor(private activatedRoute: ActivatedRoute) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe((data: { items: ${entity.name}[] }) => {
            this.items = data.items;
        });
    }
}