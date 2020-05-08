import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {empty} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {${entity.name}} from "./${entity.name?lower_case}.model";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@Injectable({
    providedIn: 'root',
})
export class ${entity.name}Resolver implements Resolve<${entity.name}> {
    constructor(private service: ${entity.name}Service) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let id = route.paramMap.get('id');
        if(!id) { // new object
            return {} as ${entity.name};
        }
        return this.service.getById(id).pipe(
            catchError((error) => {
                return empty();
            })
        );
    }
}