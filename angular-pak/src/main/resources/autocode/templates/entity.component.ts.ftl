import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {${entity.name}} from "./${entity.name?lower_case}.model";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@Component({
    selector: 'autocode-${entity.name?lower_case}',
    templateUrl: './${entity.name?lower_case}.component.html',
    styleUrls: ['./${entity.name?lower_case}.component.css']
})
export class ${entity.name}Component {
    item: ${entity.name};

    constructor(
        private service: ${entity.name}Service,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe((data: { item: ${entity.name} }) => {
            this.item = data.item;
        });
    }

    onSave() {
        this.service.save(this.item).subscribe();
        this.router.navigate(["/${entity.name?lower_case}"]);
    }

    onDelete() {
        this.service.delete(this.item).subscribe();
        this.router.navigate(["/${entity.name?lower_case}"]);
    }
}
