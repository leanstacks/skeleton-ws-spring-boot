# Project Skeleton for Spring Boot Web Services

## Acknowledgements

This is a [LEAN**STACKS**](http://www.leanstacks.com) solution.

For more detailed information and instruction about constructing Spring Boot RESTful web services, see the book [Lean Application Engineering Featuring Backbone.Marionette and the Spring Framework](https://leanpub.com/leanstacks-marionette-spring).

## Getting Started

This is a project skeleton for a [Spring Boot](http://projects.spring.io/spring-boot/) RESTful web services application.

### Features

#### RESTful Web Service Family
The project contains examples of **C** reate, **R** ead, **U** pdate, and **D** elete web services.  The project illustrates the use of `@ExceptionHandler` methods to manage web service responses when common exceptional conditions arise.

#### Business Services
The project demonstrates the encapsulation of business behaviors into domain-specific, Spring-managed services annotated with `@Service`.

#### Spring Data JPA
The project shows the use of Spring Data JPA repositories, `@Repository`, for data access and management.  Illustrates the `@Entity` annotation and other JPA entity model annotations for attribute and relationship mapping.

#### HSQLDB In-Memory Database
The project illustrates how to use the HSQLDB in-memory database which is useful for rapid prototyping or unit test execution in a continuous integration environment.  The project contains schema and data SQL scripts to build and destroy the database dynamically as the application is started and stopped.

#### MySQL Database
In addition to HSQLDB support, the project also supports integration with MySQL.  The project contains MySQL schema and data scripts.

#### Transaction Management
The project contains examples of the `@Transactional` annotation on business service methods.

#### Cache Management
The project contains examples of the `@Cacheable`, `@CachePut`, and `@CacheEvict` annotations on business service methods.

#### Scheduled (Batch) Processes
The project illustrates the use of the `@Scheduled` annotation and provides examples of cron, fixed rate, and fixed delay schedules.

#### Asynchronous Processes
The project illustrates the use of the `@Async` annotation and provides examples of asynchronous methods with and without return values.

#### Spring Security
The project provides examples of Spring Security integration.  The web service endpoints are secured using Basic Auth, backed by database authentication and authorization.  The project illustrates declarative authorization for resources by role.

#### Spring Profiles
The project demonstrates how to use Spring Profiles to activate (or deactivate) application components and configuration.  The profiles illustrated are: batch, hsqldb, and mysql.

#### Unit Tests
The project contains unit test examples for standard components such as business services or batch beans and examples for the web service endpoints using Mock objects.  Perform complete end-to-end testing with Spring MVC mocking or leverage Mockito to stub or spy business components.

#### Actuator Monitoring and Management
The project illustrates the use of Spring Boot Actuator for application monitoring and management.  The application demonstrates the recording of custom metrics and the creation of custom health checks.  Also, custom Maven project attributes are incorporated into the Actuator info endpoint.

#### API Documentation Generator
The project includes [Springfox](http://springfox.github.io/springfox/) Swagger integration to automatically generate API docs for the RESTful web service endpoints.  This feature may be activated using the *"docs"* Spring profile.

## Languages

This project is authored in Java.

## Installation

### Fork the Repository

Fork the [Spring Boot web services skeleton project](https://github.com/mwarman/skeleton-ws-spring-boot) on GitHub.  Clone the project to the host machine.

### Dependencies

The project requires the following dependencies be installed on the host machine:

* Java Development Kit 7 or later

and choose one of:
* Apache Maven 3 or later
* Gradle 2.12 or later \*

\* The Gradle Wrapper is bundled with this project. Gradle tasks may be used without installing Gradle CLI by substituting `./gradlew` for `gradle` in the instructions below.

### Spring Tool Suite or Eclipse

This project uses Checkstyle static code analysis and reporting to ensure contributions are formatted in a consistent manner.  To ease the burden for contributing software engineers, the Eclipse Java Code Formatter configuration is supplied.  The formatting configuration may be used in Eclipse, the Spring Tool Suite, or any derivative of the Eclipse IDE.

The Java Code Formatter configuration is located in the source at: `etc/eclipse/java-formatter.xml`.

After importing the project into Eclipse, edit the project properties by selecting *Properties* from the *Project* drop-down menu.  Then, expand the *Java Code Style* menu and select *Formatter*.  Click the *Import* button to import the configuration file.  Next, expand the *Java Editor* menu and select *Save Actions*.  Ensure that the following selections are checked:
* Enable project specific settings
* Perform the selected actions on save
  * Format source code
    * Format all lines
  * Organize imports

## Running

The project supports [Maven](http://maven.apache.org/) and [Gradle](http://gradle.org/) for build, package, and test workflow automation.  

### Maven

The following Maven goals are the most commonly used.

#### spring-boot:run

The `spring-boot:run` Maven goal performs the following workflow steps:

* compiles Java classes to the /target directory
* copies all resources to the /target directory
* starts an embedded Apache Tomcat server

To execute the `spring-boot:run` Maven goal, type the following command at a terminal prompt in the project base directory.

```
mvn spring-boot:run
```

Type `ctrl-C` to halt the web server.

This goal is used for local machine development and functional testing.  Use the `package` goal for server deployment.

#### test

The `test` Maven goal performs the following workflow steps:

* compiles Java classes to the /target directory
* copies all resources to the /target directory
* executes the unit test suites
* produces unit test reports

The `test` Maven goal is designed to allow engineers the means to run the unit test suites against the main source code.  This goal may also be used on continuous integration servers such as Jenkins, etc.

To execute the `test` Maven goal, type the following command at a terminal prompt in the project base directory.

```
mvn clean test
```

#### package

The `package` Maven goal performs the following workflow steps:

* compiles Java classes to the /target directory
* copies all resources to the /target directory
* executes the unit test suites
* produces unit test reports
* prepares an executable JAR file in the /target directory

The `package` Maven goal is designed to prepare the application for distribution to server environments.  The application and all dependencies are packaged into a single, executable JAR file.

To execute the `package` goal, type the following command at a terminal prompt in the project base directory.

```
mvn clean package
```

The application distribution artifact is placed in the /target directory and is named using the `artifactId` and `version` from the pom.xml file.  To run the JAR file use the following command:

```
java -jar example-1.0.0.jar
```

By default, the batch and hsqldb profiles are active.  To run the application with a specific set of active profiles, supply the `--spring.profiles.active` command line argument.  For example, to start the project using MySQL instad of HSQLDB and enable the batch process:

```
java -jar example-1.0.0.jar --spring.profiles.active=mysql,batch
```

### Gradle

The following Gradle tasks are the most commonly used.

#### bootRun

The `bootRun` Gradle task performs the following workflow steps:

* compiles Java classes to the /build directory
* copies all resources to the /build directory
* starts an embedded Apache Tomcat server

To execute the `bootRun` Gradle task, type the following command at a terminal prompt in the project base directory.

```
gradle clean bootRun

...OR...

./gradlew clean bootRun
```

Type `ctrl-C` to halt the web server.

This task is used for local machine development and functional testing.  Use the `assemble` or `build` task for server deployment.

#### assemble

The `assemble` Gradle task performs the following workflow steps:

* compiles Java classes to the /build directory
* copies all resources to the /build directory
* prepares an executable JAR file in the /build/libs directory

The `assemble` Gradle task is designed to allow engineers the means to compile the project and produce an executable JAR file suitable for server environments without executing unit tests or producing other project reports.

To execute the `assemble` Gradle task, type the following command at a terminal prompt in the project base directory.

```
gradle clean assemble

...OR...

./gradlew clean assemble
```

#### build (default)

The `build` Gradle task performs the following workflow steps:

* compiles Java classes to the /build directory
* copies all resources to the /build directory
* executes the unit test suites
* analyzes unit test code coverage
* produces unit test, code coverage, and other project reports in the /build/reports directory
* prepares an executable JAR file in the /build/libs directory

The `build` Gradle task is prepares the application for distribution to server environments. The application and all dependencies are packaged into a single, executable JAR file.

This task is ideal for use on continuous integration servers such as Jenkins, etc. because it produces unit test, code coverage, and static analysis reports.

To execute the `build` Gradle task, type the following command at a terminal prompt in the project base directory.

```
gradle clean build

...OR...

./gradlew clean build
```

The `clean` and `build` tasks are the default tasks for this project.  Therefore, simply typing `gradle` (or `./gradlew` to use the Gradle Wrapper) will produce the same result as `gradle clean build`.

```
gradle

...OR...

./gradlew
```

The application distribution artifact is placed in the /build/libs directory and is named using the project name and version from the `build.gradle` file.  To run the JAR file use the following command:

```
java -jar build/libs/example-1.0.0.jar
```

By default, the batch and hsqldb profiles are active.  To run the application with a specific set of active profiles, supply the `--spring.profiles.active` command line argument.  For example, to start the project using MySQL instad of HSQLDB and enable the batch process:

```
java -jar build/libs/example-1.0.0.jar --spring.profiles.active=mysql,batch
```

#### encodePassword

The `encodePassword` Gradle task executes the `BCryptPasswordEncoderUtil` utility class to encode password values which may be included in the sample database scripts.  The clear text password values are passed as a Gradle `-P` property arguments on the command line.

To execute the `encodePassword` Gradle task, type the following command at a terminal prompt in the project base directory.

```
gradle -q encodePassword -Pmainargs=<password>[,<password>]

...OR...

./gradlew -q encodePassword -Pmainargs=<password>[,<password>]
```

The value of the `mainargs` property is passed as the arguments to the Java main method of the `BCryptPasswordEncoderUtil` class.  Separate multiple passwords with a comma.
