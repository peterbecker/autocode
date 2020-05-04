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

    getAll(): Observable<${entity.name}> {
        return this.http.get<${entity.name}>(this.endpoint);
    }

    getById(id: string): Observable<any> {
        return this.http.get(this.endpoint + "/" + id);
    }

    save(entity: ${entity.name}): Observable<${entity.name}> {
        return this.http.put<${entity.name}>(this.endpoint + "/" + entity.id, entity);
    }

    delete(entity: ${entity.name}): Observable<any> {
        return this.http.delete(this.endpoint + "/" + entity.id);
    }
}