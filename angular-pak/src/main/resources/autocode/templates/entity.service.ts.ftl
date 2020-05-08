import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {${entity.name}} from "./${entity.name?lower_case}.model";
import {tap} from "rxjs/operators";

@Injectable({
    providedIn: 'root',
})
export class ${entity.name}Service {
    private items = new BehaviorSubject<Array<${entity.name}>>([]);

    constructor(private http: HttpClient) {
        this.loadData();
    }

    private endpoint = "/api/${entity.name?lower_case}";

    private loadData() {
        this.http.get<Array<${entity.name}>>(this.endpoint).subscribe(data => {this.items.next(data);});
    }

    getAll(): Observable<Array<${entity.name}>> {
        return this.items.asObservable();
    }

    getById(id: string): Observable<any> {
        return this.http.get(this.endpoint + "/" + id);
    }

    save(entity: ${entity.name}): Observable<${entity.name}> {
        return this.http.put<${entity.name}>(this.endpoint + "/" + entity.id, entity).pipe(
            tap(() => { this.loadData(); })
        );
    }

    delete(entity: ${entity.name}): Observable<any> {
        return this.http.delete(this.endpoint + "/" + entity.id).pipe(
            tap(() => { this.loadData(); })
        );
    }
}