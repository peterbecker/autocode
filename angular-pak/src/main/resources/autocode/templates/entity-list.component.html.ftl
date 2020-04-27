<h1>${entity.name}s</h1>
<div class="autocode-layout">
    <mat-nav-list>
        <a mat-list-item *ngFor="let item of items" [routerLink]="[item.id]" routerLinkActive="active-link">{{item.${entity.property[0].name}}}</a>
    </mat-nav-list>
    <mat-card class="autocode-right">
        <router-outlet></router-outlet>
    </mat-card>
</div>