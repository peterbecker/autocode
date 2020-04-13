import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {empty} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {${entity.name}} from "./${entity.name?lower_case}.model";
import {${entity.name}Service} from "./${entity.name?lower_case}.service";

@Injectable({
    providedIn: 'root',
})
export class ${entity.name}ListResolver implements Resolve<${entity.name}> {
    constructor(private api: ${entity.name}Service) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.api.getAll().pipe(
            catchError((error) => {
                return empty();
            })
        );
    }
}