<h1>${entity.name}s</h1>
<ul>
    <li *ngFor="let item of items">{{item.${entity.property[0].name}}}</li>
</ul>