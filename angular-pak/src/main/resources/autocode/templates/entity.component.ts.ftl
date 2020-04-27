import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {${entity.name}} from "./${entity.name?lower_case}.model";

@Component({
    selector: 'autocode-${entity.name?lower_case}',
    templateUrl: './${entity.name?lower_case}.component.html',
    styleUrls: ['./${entity.name?lower_case}.component.css']
})
export class ${entity.name}Component {
    item: ${entity.name};

    constructor(private activatedRoute: ActivatedRoute) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe((data: { item: ${entity.name} }) => {
            this.item = data.item;
        });
    }
}
