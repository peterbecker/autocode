<h1>${entity.name}s</h1>
<div class="autocode-layout">
    <ul>
        <li *ngFor="let item of items"><a [routerLink]="[item.id]" routerLinkActive="active-link">{{item.${entity.property[0].name}}}</a></li>
    </ul>
    <div class="autocode-right">
        <router-outlet></router-outlet>
    </div>
</div>