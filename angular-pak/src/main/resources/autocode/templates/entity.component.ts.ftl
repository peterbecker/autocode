import {Component} from '@angular/core';

@Component({
    selector: 'autocode-${entity.name?lower_case}',
    templateUrl: './${entity.name?lower_case}.component.html',
    styleUrls: ['./${entity.name?lower_case}.component.css']
})
export class ${entity.name}Component {
}
