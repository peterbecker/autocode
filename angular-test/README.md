# TestApp

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 9.1.0.

It has been integrated into the Maven build using the [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) to
run scripts in the `package.json` file, and package the output into a ZIP file which is managed by Maven. Otherwise this is a
normal NPM build, development work should not require touching the Maven side at all. Only if the build changes there may be
a need to make sure the plugin configured in `pom.xml` still calls the right scripts in `package.json`.

## Development server

The development setup expects a backend service to be running. To do that, follow the instructions in the
`dropwizard-test` module.

Run `npm run start` for a dev server, backed by a reverse proxy. Navigate to `http://localhost:4200/`. The app will automatically 
reload if you change any of the source files.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a 
production build.
