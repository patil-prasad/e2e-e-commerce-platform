# E2E E-Commerce Platform

A microservices-based e-commerce platform built with Java and Spring Boot, designed for high scalability and reliability.

## Features

- **User Service**: Handles user authentication and profile management
- **Catalog Service**: Manages product inventory and categories
- **Cart Service**: Manages shopping cart operations
- **Order Service**: Processes and tracks orders
- **API Gateway**: Single entry point for all client requests

## Project Structure

```
.
├── ApiGateway/         # API Gateway service
├── CartService/        # Shopping cart service
├── CatalogService/     # Product catalog service
├── OrderService/       # Order processing service
├── UserService/        # User management service
├── docker/             # Docker configuration
├── docker-compose.yml  # Docker Compose configuration
└── LoadTest.jmx        # JMeter load test configuration
```

## Prerequisites

- Java 17 or higher
- Gradle 7.0+
- Docker 20.10+
- Docker Compose 2.0+

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/e2e-e-commerce-platform.git
   cd e2e-e-commerce-platform
   ```

2. **Build the project**
   ```bash
   ./gradlew clean build
   ```

## Running with Docker

Start all services using Docker Compose:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`

## Load Testing

Load tests are configured in `LoadTest.jmx` using JMeter. To run the load tests:

1. Install JMeter
2. Open `LoadTest.jmx` in JMeter
3. Configure thread groups as needed
4. Run the test