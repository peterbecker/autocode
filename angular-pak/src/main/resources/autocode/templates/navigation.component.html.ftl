<ul id="autocode-navigation">
<#list entities as entity>
    <li><a [routerLink]="['/${entity.name?lower_case}']">${entity.name}</a></li>
</#list>
</ul>