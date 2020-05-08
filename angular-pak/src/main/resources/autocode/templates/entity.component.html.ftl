<form>
<#list entity.property as property>
    <div>
        <mat-form-field appearance="fill">
            <mat-label>${property.name}:</mat-label>
    <#if property.type == 'Date'>
            <input matInput name="${property.name}" [(ngModel)]="item.${property.name}" [matDatepicker]="${property.name}Datepicker">
            <mat-datepicker-toggle [for]="${property.name}Datepicker"></mat-datepicker-toggle>
            <mat-datepicker #${property.name}Datepicker></mat-datepicker>
    <#else>
            <input matInput name="${property.name}" [(ngModel)]="item.${property.name}">
    </#if>
        </mat-form-field>
    </div>
</#list>
</form>
<div class="actionButtons">
    <button mat-flat-button color="primary" (click)="onSave()">Save</button>
    <button mat-flat-button color="warn" (click)="onDelete()">Delete</button>
</div>
