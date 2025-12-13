# E-Commerce Microservices Architecture

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

A comprehensive e-commerce platform built with Spring Boot microservices architecture, featuring service discovery, API gateway, and containerized deployment.

## 🏗️ Architecture Overview

This project implements a microservices architecture for an e-commerce system with the following key components:

- **Service Discovery**: Eureka Server for dynamic service registration and discovery
- **API Gateway**: Centralized entry point for all client requests with routing and load balancing
- **Microservices**: Domain-driven design with separate services for each business capability
- **Databases**: PostgreSQL for inventory service with potential for multi-database setup
- **Containerization**: Docker and Docker Compose for easy deployment and scaling

## 📋 Services

| Service | Port | Domain | Description |
|---------|------|--------|-------------|
| **Discovery Server** | `8761` | Service Registry | Eureka server for service discovery |
| **API Gateway** | `8083` | API Gateway | Centralized routing and load balancing |
| **User Service** | `8081` | User Management | User registration, authentication, profiles |
| **Product Service** | `3000` | Product Catalog | Product management, categories, inventory |
| **Cart Service** | `3001` | Shopping Cart | Cart operations, items management |
| **Order Service** | `8082` | Order Processing | Order creation, payment, fulfillment |
| **Inventory Service** | `3004` | Inventory Management | Stock tracking, warehouse management |
| **Notification Service** | `8080` | Notifications | Event-driven notifications via Kafka |

## 🛠️ Technology Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.5.7** - Framework for microservices
- **Spring Cloud 2025.0.0** - Cloud-native patterns and tools

### Infrastructure & Tools
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway
- **Docker & Docker Compose** - Containerization
- **PostgreSQL 15** - Primary database
- **Maven** - Build tool and dependency management

### Development Tools
- **Spring Boot DevTools** - Development utilities
- **Spring Boot Actuator** - Production monitoring
- **Lombok** - Code generation
- **MapStruct** - Object mapping

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Docker and Docker Compose
- Maven 3.6+

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd microservice
   ```

2. **Start infrastructure services**
   ```bash
   docker-compose up -d inventory-db discovery-server
   ```

3. **Build all services**
   ```bash
   mvn clean package -DskipTests
   ```

4. **Start services individually or use Docker Compose**
   ```bash
   # Option 1: Using Docker Compose (recommended for full stack)
   docker-compose up -d

   # Option 2: Run services individually
   java -jar discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar
   java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
   # ... start other services
   ```

5. **Verify deployment**
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8083
   - Individual services on their respective ports

### Docker Deployment

```bash
# Build and start all services
docker-compose up --build

# Start in detached mode
docker-compose up -d

# View logs
docker-compose logs -f [service-name]

# Stop all services
docker-compose down
```

## 📁 Project Structure

```
microservice/
├── discovery-server/          # Eureka service discovery server
├── api-gateway/              # API gateway service
├── user-service/             # User management service
├── product-service/          # Product catalog service
├── cart-service/             # Shopping cart service
├── order-service/            # Order processing service
├── inventory-service/        # Inventory management service
├── notification-service/     # Notification service (Kafka event-driven)
├── kafka-docker/             # Kafka infrastructure (commented out)
├── docker-compose.yml        # Docker orchestration
├── pom.xml                   # Maven parent POM
└── README.md                 # This file
```

## 🔧 Configuration

### Environment Variables

Each service uses `.env` files for configuration. Key configurations include:

- **Database connections** (PostgreSQL credentials)
- **Service discovery URLs** (Eureka server location)
- **External service endpoints** (payment gateways, email services)
- **Security settings** (JWT secrets, API keys)

### Service URLs

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8083
- **User Service**: http://localhost:8081
- **Product Service**: http://localhost:3000
- **Cart Service**: http://localhost:3001
- **Order Service**: http://localhost:8082
- **Inventory Service**: http://localhost:3004
- **Notification Service**: http://localhost:8080

## 🧪 Testing

```bash
# Run tests for all modules
mvn test

# Run tests for specific service
cd [service-name]
mvn test

# Run integration tests
mvn verify
```

## 📊 Monitoring & Observability

- **Spring Boot Actuator**: Health checks, metrics, and monitoring endpoints
- **Eureka Dashboard**: Service registry and health status
- **Docker Logs**: Container logs and debugging information

Access actuator endpoints:
- Health: `http://localhost:[port]/actuator/health`
- Info: `http://localhost:[port]/actuator/info`
- Metrics: `http://localhost:[port]/actuator/metrics`

## 🔒 Security

- JWT-based authentication
- Role-based access control (RBAC)
- API gateway security filters
- Secure communication between services
- Environment-specific security configurations

## 📚 API Documentation

API documentation is available through Spring Boot Actuator or can be configured with Swagger/OpenAPI.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Development Guidelines

- Follow domain-driven design principles
- Implement comprehensive unit and integration tests
- Use meaningful commit messages
- Maintain API contracts and backward compatibility
- Document new features and API changes

## 🚧 Future Enhancements

- [ ] Add Redis caching layer
- [ ] Implement comprehensive API documentation with Swagger
- [ ] Implement distributed tracing
- [ ] Add CI/CD pipeline
- [ ] Implement service mesh (Istio)
- [ ] Add monitoring dashboard (Grafana/Prometheus)

## 📞 Support

For questions or issues:
- Check the service-specific README files
- Review Docker Compose logs
- Verify Eureka service registration
- Check actuator health endpoints

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Last Updated**: December 13, 2025
**Version**: 1.0.0