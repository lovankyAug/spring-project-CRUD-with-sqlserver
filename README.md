# Spring Boot Learning Project

This is a project I built while studying the Spring Framework and learning about RESTful APIs.

## Features
- CRUD operations for User
- Spring Data JPA
- RESTful API
- SQL Server integration

## Technologies
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- SQL server

## How to Run
1. Clone this repository

2. Create and config file application.yaml 
- location: root/src/resources/application.yaml
- content: 
`server:
  port: 8080
  servlet:
    context-path: /identity`
`spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=yourDatabaseName;encrypt=true;trustServerCertificate=true
    username: your username
    password: your password
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.SQLServerDialect`
- 

3. Run the application using your IDE or `mvn spring-boot:run`

## Test with Postman
1. Insert new user 
- method: POST 
- url: http://localhost:8080/identity/users 
- body(JSON):
- ``{
    "userName": "test1",
    "password": "123",
    "email": "abc@gmail.com",
    "dob": "2002-01-01", 
    "address": "address"
}``
2. Get all of user 
- method: GET 
- url: http://localhost:8080/identity/users
3. Get user by ID
- method: GET
- url: http://localhost:8080/identity/users/example-id
    change example-id by an id already exist.
4. Update user information
- method: POST 
- url: http://localhost:8080/identity/users/example-id
    change example-id by an id already exist.
- body(JSON):
- ``{
    "userName": "newUserName",
    "password": "newPassword",
    "email": "newEmail@gmail.com",
    "dob": "2002-01-01", 
    "address": "newAddress"
}``
5. Get user by ID
- method: DELETE
- url: http://localhost:8080/identity/users/example-id
  change example-id by an id already exist.
## Author
- name: lovankydev
- country: Vietnam 
- Occupation: Student

## Contact
1. Email: lovankydev2002@gmail.com
2. Phone: (+84)865305996