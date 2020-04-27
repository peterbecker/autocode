<#list entity.property as property>
<form>
    <div>
        <mat-form-field appearance="fill">
            <mat-label>${property.name}:</mat-label>
            <input matInput name="${property.name}" [(ngModel)]="item.${property.name}">
        </mat-form-field>
    </div>
</form>
</#list>
