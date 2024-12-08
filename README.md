# Spring Boot Aplication - Task CRUD

## Pre Run

This project use Java 21 Amazon Corretto.

## Essentials

Execute the following maven command: `mvn clean install`

After execute: `mvn compile`

This application have two controllers. One for get the health check (life status) and the second for make de CRUD operations.

To initiate the application we have to run TodoappApplication
Focal point: This application use a H2 database and JPA. This H2 database initiates with the run of the main application. If you check the application resources, the port of the host is the 8080.

Paste in the browser the following URL to connect to the database: http://localhost:8080/h2-console, the local credentials are in the application resources too.

## API Controller

Paste in the browser http://localhost:8080/swagger-u for access to the Swagger UI API documentation.

Use postman o curl for sending the requests.

### Health Check
http://localhost:8080/health-check

### Task Controller for CRUD
+ DELETE http://localhost:8080/tasks/{id}
+ GET ALL TASK http://localhost:8080/tasks
+ GET TASK BY ID http://localhost:8080/tasks/{id}
+ POST NEW TASK http://localhost:8080/tasks
  + BODY CONSTRUCTOR 
  + {
    "title": "Example 1",
    "description": "Description 1",
    "completed": false
    }
  + {
    "id": 1,
    "title": "Example 1",
    "description": "Description 1",
    "completed": true
    }
+ UPDATE TASK http://localhost:8080/tasks/{id}
  + UTILIZE BODY CONSTRUCTOR SAME AS POST REQUEST
