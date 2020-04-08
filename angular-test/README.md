# TestApp

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 9.1.0.

It has been integrated into the Maven build using the [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) to
run scripts in the `package.json` file, and package the output into a ZIP file which is managed by Maven. Otherwise this is a
normal NPM build, development work should not require touching the Maven side at all. Only if the build changes there may be
a need to make sure the plugin configured in `pom.xml` still calls the right scripts in `package.json`.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
