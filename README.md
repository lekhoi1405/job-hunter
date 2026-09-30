<div align="center">

# 💼 Recruitment Platform API

**A RESTful backend API for managing recruitment workflows, built with Java and Spring Boot.**

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.10-brightgreen?style=flat-square&logo=spring-boot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green?style=flat-square&logo=spring-security)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=flat-square&logo=mysql)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?style=flat-square&logo=gradle)

</div>

---

## 📖 Overview

Recruitment Platform API is a backend application for managing core recruitment data such as **users, companies, jobs, and skills**.

The project focuses on backend fundamentals including authentication, relational data modeling, transaction management, API validation, pagination, dynamic filtering, consistent API responses, and data lifecycle handling.

---

## ✨ Key Features

- 🔐 **JWT Authentication**
  - Authentication with Spring Security
  - JWT access tokens signed using HS512
  - Database-backed UUID refresh tokens
  - Refresh tokens stored in `HttpOnly` and `Secure` cookies
  - Refresh-token rotation and logout invalidation

- 👤 **Recruitment Domain Management**
  - User management
  - Company management
  - Job management
  - Skill management
  - Job–Skill many-to-many relationship through an explicit join entity

- 🗄️ **Persistence & Data Consistency**
  - JPA/Hibernate entity relationships
  - Lazy loading and selective fetch strategies
  - Transactional service operations
  - JPQL bulk operations for relationship-aware deletion

- 🔎 **Pagination & Dynamic Filtering**
  - Spring Data pagination
  - Dynamic filtering using JPA `Specification`
  - Generic search criteria reusable across multiple resources

- ✅ **DTO & Validation Layer**
  - Request/response DTOs
  - Jakarta Bean Validation
  - MapStruct entity–DTO mapping

- 📦 **Consistent API Responses**
  - Centralized response wrapping using `ResponseBodyAdvice`
  - Global exception handling using `@ControllerAdvice`

- 🕒 **Entity Auditing**
  - Automatic `createdAt`, `updatedAt`, `createdBy`, and `updatedBy`
  - Implemented through JPA entity lifecycle listeners

---

## 🛠️ Tech Stack

| Area | Technologies |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.5.10 |
| Web | Spring MVC |
| Security | Spring Security, OAuth2 Resource Server, Nimbus JWT |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Mapping | MapStruct |
| Validation | Jakarta Bean Validation |
| Build Tool | Gradle 8.14.4 |
| Utilities | Lombok |

---

## 🏗️ Project Structure

```text
src/main/java/com/lekhoi/recruitment/
│
├── config/             # Security, CORS and application configuration
├── controller/         # REST API controllers
│   └── auth/           # Authentication endpoints
│
├── domain/
│   ├── base/           # Base entities and auditing
│   ├── dto/            # Request and response DTOs
│   ├── entity/         # JPA entities
│   └── specification/  # Dynamic filtering specifications
│
├── repository/         # Spring Data JPA repositories
├── service/            # Business logic and transactions
│   └── mapper/         # MapStruct mappers
│
└── util/               # Security utilities, responses and exception handling
```

The application follows a layered backend structure:

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL
```

DTOs are used between the API layer and service layer to avoid exposing persistence entities directly.

---

## 🔐 Authentication Flow

```text
Login
  │
  ▼
Username / Password Authentication
  │
  ├── Access Token (JWT)
  │
  └── Refresh Token (UUID)
           │
           ▼
      Stored in Database
           +
      HttpOnly Cookie
```

### Login

```http
POST /auth/login
```

After successful authentication:

- the access token is returned in the response body;
- the refresh token is stored in the database;
- the refresh token is returned as an `HttpOnly` cookie.

### Access protected APIs

Send the access token using:

```http
Authorization: Bearer <access_token>
```

### Refresh session

```http
GET /auth/refresh
```

The server validates the existing refresh token, creates a new access token and refresh token, then removes the previous refresh token.

### Logout

```http
POST /auth/logout
```

The refresh token is removed from the database and its cookie is expired.

---

## 🌐 API Modules

| Resource | Base Endpoint | Operations |
| --- | --- | --- |
| Authentication | `/auth` | Login, refresh, logout, current account |
| Users | `/users` | Create, read, update, delete, list |
| Companies | `/companies` | Create, read, update, delete, list |
| Jobs | `/jobs` | Create, update, delete, list |
| Skills | `/skills` | Create, update, delete, list |

Protected endpoints require a valid JWT access token. Authentication is publicly available through `/auth/login` and `/auth/refresh`.

---

## 🔎 Pagination & Filtering

List endpoints support pagination:

```http
GET /users?current=1&pageSize=10
```

Dynamic filters can also be supplied through the `filter` parameter:

```http
GET /users?current=1&pageSize=10&filter=name:john
```

Multiple criteria can be combined using commas.

The filtering mechanism is implemented with Spring Data JPA `Specification`, allowing the same filtering infrastructure to be reused across users, companies, jobs, and skills.

---

## 🗃️ Data Model

The main domain relationships include:

```text
Company
 ├── Users
 └── Jobs
       │
       ▼
   JobSkill
       ▲
       │
     Skills

User
 └── RefreshTokens
```

`JobSkill` is modeled as a dedicated join entity instead of a direct `@ManyToMany` relationship, allowing the relationship to have its own lifecycle and persistence behavior.

---

## ⚙️ Configuration

Sensitive configuration is not stored directly in the repository.

Create an `application-local.properties` file in the project root:

```properties
DB_URL=jdbc:mysql://localhost:3306/<your_database>
DB_USERNAME=<your_username>
DB_PASSWORD=<your_password>

JWT_BASE64_SECRET=<your_base64_secret>
```

The application loads these values through:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

app.jwt.base64-secret=${JWT_BASE64_SECRET}
```

`application-local.properties` is excluded from Git through `.gitignore`.

---

## 🚀 Getting Started

### Prerequisites

- JDK 21
- MySQL
- Git

### Clone the repository

```bash
git clone <repository-url>
cd recruitment-platform-api
```

### Configure the database

Create a MySQL database and configure your local properties as described above.

### Run the application

Windows:

```bash
gradlew.bat bootRun
```

Linux / macOS:

```bash
./gradlew bootRun
```

The application runs on:

```text
http://localhost:8081
```

---

## 📡 API Response Format

Successful API responses are wrapped in a consistent structure:

```json
{
  "statusCode": 200,
  "error": null,
  "message": "Fetch all users",
  "data": {}
}
```

Validation and business errors are handled through centralized exception handling.

---

## 📌 Project Focus

This project was built to practice and demonstrate backend development concepts including:

- REST API design
- Spring Security authentication
- JWT and refresh-token lifecycle
- JPA/Hibernate relationship management
- Transaction boundaries and data consistency
- DTO mapping and request validation
- Pagination and dynamic querying
- Centralized response and exception handling

---

## 👨‍💻 Author

**Nguyen Le Anh Khoi**

Backend-focused developer working with **Java and Spring Boot**.
