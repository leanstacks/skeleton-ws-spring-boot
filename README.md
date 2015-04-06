# Project Skeleton for Spring Boot Web Services

## Acknowledgements

This is a [LEAN**STACKS**](http://www.leanstacks.com) solution.

For more detailed information and instruction about constructing Spring Boot RESTful web services, see the book [Lean Application Engineering Featuring Backbone.Marionette and the Spring Framework](https://leanpub.com/leanstacks-marionette-spring).

## Getting Started

This is a project skeleton for a [Spring Boot](http://projects.spring.io/spring-boot/) RESTful web services application.

### Features

#### RESTful Web Service Family
The project contains examples of **C**reate, **R**ead, **U**pdate, and **D**elete web services.  The project illustrates the use of `@ExceptionHandler` methods to manage web service responses when common exceptional conditions arise.

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
The project contains unit test examples for standard components such as business services or batch beans and examples for the web service endpoints using Mock objects.

#### Actuator Monitoring and Management
The project illustrates the use of Spring Boot Actuator for application monitoring and management.  The application demonstrates the recording of custom metrics.  Also, custom Maven project attributes are incorporated into the Actuator info endpoint.

## Languages

This project is authored in Java.

## Installation

### Fork the Repository

Fork the [Spring Boot web services skeleton project](https://github.com/mwarman/skeleton-ws-spring-boot) on GitHub.  Clone the project to the host machine.

### Dependencies

The project requires the following dependencies be installed on the host machine:

* Java Development Kit 7 or later
* Apache Maven 3 or later

## Running

The project uses [Maven](http://maven.apache.org/) for build, package, and test workflow automation.  The following Maven goals are the most commonly used.

### spring-boot:run

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

### test

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

### package

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
