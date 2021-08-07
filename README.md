# Technologies used

* Spring Boot 2.2.4.RELEASE
* Java 8
* H2
* Lombok
* JUnit
* Mockito
* Swagger

&nbsp;
&nbsp;
## Command to launch API via maven
```shell
mvn clean install spring-boot: run
```

## H2
API database, chosen for being free and for its simplicity to implement.

For the persisted data to remain when the application restarts, it is necessary to change the following property in **application.yml**
```shell
spring:
  h2:
    console:
      enabled: true
      path: /h2
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop
```

**To**

```shell
spring:
  h2:
    console:
      enabled: true
      path: /h2
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```

## Lombok
Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java. It's necessary to install it in the IDE, link to download [https://projectlombok.org/download].

## JUnit
JUnit is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.

## Mockito
Mockito is a unit testing framework and its main objective is to instantiate classes and control the behavior of methods.

## Swagger
Swagger is a framework for describing, consuming and viewing REST services. 

&nbsp;
&nbsp;
# Technical Challenge
**Objective:** In cooperativism, each member has one vote and decisions are taken in assemblies, by vote. From there, you need to create a back-end solution to manage these voting sessions.
This solution must run in the cloud and promote the following features through a REST API:

* Register a new agenda;
* Open a voting session on an agenda (the voting session must be open for a specified amount of time in the opening call or 1 minute by default);
* Receive votes from members on agendas (votes are only 'Yes'/'No'. Each member is identified by a unique id and can vote only once per agenda);
* Count the votes and give the result of the vote on the agenda.

For exercise purposes, the security of the interfaces can be abstracted and any calls to the interfaces can be considered as authorized. The choice of language, frameworks and libraries is free (as long as it does not infringe usage rights).

It is important that the guidelines and votes are persisted and that they are not lost when the application restarts.

## Bonus tasks

Bonus tasks are not mandatory, but allow us to assess other knowledge you may have. We always suggest that the candidate ponder and present how far he can do it, considering his level of knowledge and the quality of delivery.

**Bonus Task 1** - Integration with external systems

Integrate with a system that checks, from the member's CPF, whether he can vote
* GET [https://user-info.herokuapp.com/users/{cpf}]
* If the CPF is invalid, the API will return HTTP Status 404 (Not found). You can use CPF generators to generate valid CPFs;
* If the CPF is valid, the API will return if the user can (ABLE_TO_VOTE) or cannot (UNABLE_TO_VOTE) perform the operation

**Bonus Task 2** - Messaging and Lines

* The result of the vote needs to be informed to the rest of the platform, this should preferably be done through messaging. When the voting session closes, post a message with the result of the vote.

**Bonus Task 3** - Performance

* Imagine that your application can be used in scenarios where there are hundreds of thousands of votes. She must behave performatively in these settings;
* Performance tests are a good way to ensure and observe how your application performs.

**Bonus Task 4** - API Versioning

* How would you version your application's API? What strategy to use?

## What will be analyzed
* Simplicity in solution design (avoid over engineering)
* Code organization
* Project architecture
* Good programming practices (maintainability, readability, etc.)
* Possible bugs
* Error and exception handling
* Brief explanation of why choices were made during solution development
* Use of automated testing and quality tools
* Code cleaning
* Code and API documentation
* Application logs
* Messages and organization of commits

## Important notes
* Do not start the test without solving all doubts
* We will run the application to test it, take care of any external dependencies and make it clear if there are special instructions for running it
* Test your solution well, avoid bugs