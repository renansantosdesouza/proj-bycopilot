# Customer API

A RESTful API for customer management using Hexagonal Architecture built with Spring Boot.

## Technologies

- Java 21
- Spring Boot 3.1.5
- Spring Data JPA
- Oracle Database
- Lombok
- MapStruct
- OpenAPI (Swagger)
- JUnit 5 & Mockito

## Architecture

This project follows Hexagonal Architecture (Ports and Adapters) principles:

```
src/
├── main/
│   └── java/
│       └── com/copilot/myapi/
│           ├── application/        # Application layer
│           │   ├── controller/     # REST controllers
│           │   ├── dto/           # Data Transfer Objects
│           │   └── mapper/        # DTO <-> Entity mappers
│           └── infrastructure/     # Infrastructure layer
│               ├── entity/        # JPA entities
│               └── repository/    # Spring Data repositories
```

## Features

- CRUD operations for Customer management
- Address management for each customer
- API documentation with Swagger
- Multiple environment configurations (local, homolog, prod)
- Health check endpoints
- Database connection pooling with HikariCP

## Getting Started

### Prerequisites

- Java 21
- Maven
- Oracle Database

### Configuration

The application uses YAML configuration files for different environments:

- `application.yml`: Common configurations
- `application-local.yml`: Local development settings
- `application-homolog.yml`: Homologation environment
- `application-prod.yml`: Production environment

#### Environment Variables

Production environment requires the following variables:
```
DB_HOST=your-oracle-host
DB_PORT=1521
DB_SID=your-sid
DB_USERNAME=your-username
DB_PASSWORD=your-password
DB_SCHEMA=your-schema
```

#### Database Configuration

- Connection Pool: HikariCP
  - Maximum Pool Size: 20 (Production)
  - Minimum Idle: 5
  - Connection Timeout: 20000ms
  - Idle Timeout: 300000ms

#### Technical Details

- REST API follows Richardson Maturity Model Level 2
- Hexagonal Architecture with clear separation of:
  - Domain Entities
  - DTOs (using Java Records)
  - Mappers (using MapStruct)
  - Infrastructure Layer (JPA/Hibernate)
- Bean Validation for request validation
- Error handling with proper HTTP status codes
- Metrics and health checks via Spring Actuator

### Running the Application

```bash
# Local development
./mvnw spring-boot:run

# Specific profile
./mvnw spring-boot:run -Dspring.profiles.active=prod
```

### API Documentation

Access Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`

## Testing

Run the tests using Maven:

```bash
./mvnw test
```

## Endpoints

- `POST /api/v1/customers` - Create customer
- `GET /api/v1/customers` - List all customers
- `GET /api/v1/customers/{id}` - Get customer by ID
- `PUT /api/v1/customers/{id}` - Update customer
- `DELETE /api/v1/customers/{id}` - Delete customer

## Health Check

Monitor application health:
- `GET /api/v1/actuator/health` - Health status
- `GET /api/v1/actuator/info` - Application info
- `GET /api/v1/actuator/metrics` - Application metrics