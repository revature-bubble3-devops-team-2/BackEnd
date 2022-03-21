# Bubble Startup Documentation



This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 12.2.10.



## Installation

* Java
   * Install [Java](https://www.java.com/en/download/manual.jsp)
* Jenkins
  * Install [Docker](http://docker.com)
  * Install Jenkins in Docker
     > docker run -d -p 8080:8080 -p 50000:50000 jenkins/jenkins
  * Install admin
     > docker exec -t -i (image name) cat/var/jenkins/home/secrets/initialAdminPassword
  * Install suggested plugins
  

* Spring Data
  > Install dependencies from [SpringInitialzer](https://start.spring.io/)
* Spring boot
  > Install dependencies from [SpringInitialzer](https://start.spring.io/)
* Angular 4
  * Install [NodeJS](https://nodejs.org/en/)
  * Install the CLI using the npm package manager:
     > npm install -g @angular/cli
* GCP Cloud SQL
   * In the Google Cloud Console create a Google Cloud Project
   * Create a PostgreSQL instance
   * Create a PostgreSQL database on the instance
   * Connect to the instance with a PostgreSQL client
* GCP Compute Engine
   * Create a Google Compute Engine on the Google Cloud Platform.
   * Under the "Boot Disk" option, select change.
      >Under "Operating System", select Container Optimized OS. (This will come with Docker installed)
      >Select a minimum of 10GB in size. (Docker will use about this much in combination with Jenkins).
   * After your Virtual Machine has been created, click the "SSH" option to connect to your virtual machine.

## Development server

Navigate to the Angular Application
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
