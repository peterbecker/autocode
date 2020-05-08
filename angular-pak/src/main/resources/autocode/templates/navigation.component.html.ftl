<mat-sidenav-container class="sidenav-container">
    <mat-sidenav #drawer class="sidenav" fixedInViewport
            [attr.role]="(isHandset$ | async) ? 'dialog' : 'navigation'"
            [mode]="(isHandset$ | async) ? 'over' : 'side'"
            [opened]="(isHandset$ | async) === false">
        <mat-toolbar>Menu</mat-toolbar>
        <mat-nav-list>
<#list entities as entity>
            <a mat-list-item href="#" [routerLink]="['/${entity.name?lower_case}']">${entity.name}</a>
</#list>
        </mat-nav-list>
    </mat-sidenav>
    <mat-sidenav-content>
        <mat-toolbar color="primary">
            <button
                    type="button"
                    aria-label="Toggle sidenav"
                    mat-icon-button
                    (click)="drawer.toggle()"
                    *ngIf="isHandset$ | async">
                <mat-icon aria-label="Side nav toggle icon">menu</mat-icon>
            </button>
            <span>test-app</span>
        </mat-toolbar>
        <router-outlet></router-outlet>
    </mat-sidenav-content>
</mat-sidenav-container>
