import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {${entity.name}} from "./${entity.name?lower_case}.model";

@Injectable({
    providedIn: 'root',
})
export class ${entity.name}Service {
    constructor(private http: HttpClient) {
    }

    private endpoint = "/api/${entity.name?lower_case}";

    getAll(): Observable<any> {
        return this.http.get(this.endpoint);
    }
}