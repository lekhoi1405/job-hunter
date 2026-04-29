# 🎯 JobHunter - RESTful API System
![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=flat-square&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=flat-square&logo=mysql)
![Security](https://img.shields.io/badge/Spring_Security-JWT-red?style=flat-square&logo=spring-security)
## 📖 Introduction

JobHunter is a robust backend RESTful API designed to manage job recruitment processes. This project focuses on providing secure, scalable, and standardized APIs for managing users and companies. It is built with a strict **3-Layer Architecture**.

## ✨ Key Features

- **Secure Authentication:** Stateless login mechanism using Spring Security and Nimbus JWT.
- **Standardized Responses:** All APIs return a consistent JSON format using `ResponseBodyAdvice`.
- **Global Error Handling:** Centralized exception management to handle bad requests and business logic errors gracefully via `@RestControllerAdvice`.
- **Automated Data Auditing:** Automatically tracks record creation and updates (`createdAt`, `updatedBy`, etc.) using JPA `@EntityListeners`.
- **Data Transfer Object (DTO):** Strictly separates database entities from client-facing models using Lombok `@Builder` and Jakarta Validation.

## 🗂️ Project Structure

The project follows a standard layered architecture:

```text
src/main/java/Group/Artifact/
├── config/       # Security and App configurations
├── controller/   # REST API Endpoints
├── domain/       # Entities, DTOs, and Audit Listeners
├── repository/   # Database access interfaces (Spring Data JPA)
├── service/      # Business logic processing
└── util/         # Exception handlers, Response formatters, Security Utils
```

Setup & Installation

1. Prerequisites

   JDK 21 installed.
   MySQL server running locally.

2. Database Configuration
Create a new database named JobHunter. Open src/main/resources/application.properties and update your MySQL credentials:
   spring.datasource.url=jdbc:mysql://localhost:3306/JobHunter
   spring.datasource.username=your_username
   spring.datasource.password=your_password

3. Running the Application
Run the ArtifactApplication.java file from your IDE. The server will start automatically on port 8081.

API Documentation & Testing (Postman)
   make testing easier, a complete Postman collection is provided.

   Download the JobHunter_Postman.json file located in the root folder of this repository.

   Open Postman -> Click Import -> Select the downloaded JSON file.

Note: Call the POST /login API first to generate an access token. Use this token in the Authorization header (Bearer Token) to access other secured endpoints like /companies.
