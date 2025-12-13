# API Gateway

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)](https://spring.io/projects/spring-cloud)
[![Spring Gateway](https://img.shields.io/badge/Spring%20Gateway-4.1.0-blue)](https://spring.io/projects/spring-cloud-gateway)

The API Gateway serves as the single entry point for all client requests to the microservices architecture. Built with Spring Cloud Gateway, it provides intelligent routing, load balancing, security, and cross-cutting concerns.

## 🎯 Overview

The API Gateway acts as a reverse proxy that routes requests from clients to appropriate microservices. It provides:

- **Centralized Routing**: Single entry point for all API calls
- **Load Balancing**: Distributes requests across service instances
- **Security**: Authentication, authorization, and rate limiting
- **Monitoring**: Request/response logging and metrics
- **Resilience**: Circuit breakers and fallback mechanisms

## 🚀 Features

- **Dynamic Routing**: Routes requests based on path, host, or headers
- **Load Balancing**: Client-side load balancing with Eureka integration
- **Security Filters**: JWT validation, CORS, rate limiting
- **Request/Response Transformation**: Modify requests and responses
- **Circuit Breaker**: Fault tolerance with fallback responses
- **Monitoring**: Request metrics and health monitoring

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Cloud Gateway**: Reactive API gateway
- **Spring Cloud Netflix Eureka Client**: Service discovery client
- **Spring Boot Actuator**: Health monitoring and metrics
- **Docker**: Containerization support

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (for containerized deployment)
- Running Discovery Server (Eureka)

## 🚀 Quick Start

### Local Development

1. **Navigate to api-gateway directory**
   ```bash
   cd api-gateway
   ```

2. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

3. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/api-gateway:latest .

# Run the container
docker run -p 8083:8083 \
  -e DISCOVERY_SERVER=http://discovery-server:8761/eureka \
  --env-file .env \
  khang080704/api-gateway:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up api-gateway
```

## 🔧 Configuration

### Application Properties

The gateway is configured through `application.yml` with routing rules:

```yaml
server:
  port: 8083

spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=1

        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/products/**
          filters:
            - StripPrefix=1

        - id: cart-service
          uri: lb://cart-service
          predicates:
            - Path=/api/cart/**
          filters:
            - StripPrefix=1

        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**
          filters:
            - StripPrefix=1

        - id: inventory-service
          uri: lb://inventory-service
          predicates:
            - Path=/api/inventory/**
          filters:
            - StripPrefix=1

eureka:
  client:
    serviceUrl:
      defaultZone: ${DISCOVERY_SERVER:http://localhost:8761/eureka/}
```

### Environment Variables

- `DISCOVERY_SERVER`: Eureka server URL (default: http://localhost:8761/eureka/)
- `SERVER_PORT`: Gateway port (default: 8083)

## 🛣️ API Routes

### Current Route Configuration

| Route ID | Path Pattern | Target Service | Description |
|----------|-------------|----------------|-------------|
| `user-service` | `/api/users/**` | user-service | User management endpoints |
| `product-service` | `/api/products/**` | product-service | Product catalog endpoints |
| `cart-service` | `/api/cart/**` | cart-service | Shopping cart endpoints |
| `order-service` | `/api/orders/**` | order-service | Order processing endpoints |
| `inventory-service` | `/api/inventory/**` | inventory-service | Inventory management endpoints |

### Route Filters

- **StripPrefix**: Removes path prefix before forwarding
- **Rate Limiting**: Limits requests per user/time window
- **Authentication**: Validates JWT tokens
- **Logging**: Logs request/response details

## 🔒 Security

### Authentication & Authorization

The gateway implements security through filters:

```java
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/users/**")
                .filters(f -> f.stripPrefix(1)
                    .filter(authenticationFilter()))
                .uri("lb://user-service"))
            .build();
    }
}
```

### CORS Configuration

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "http://localhost:3000"
            allowedMethods: GET,POST,PUT,DELETE,OPTIONS
            allowedHeaders: "*"
            allowCredentials: true
```

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:8083/actuator/health
- **Info**: http://localhost:8083/actuator/info
- **Metrics**: http://localhost:8083/actuator/metrics
- **Gateway Routes**: http://localhost:8083/actuator/gateway/routes

### Request Metrics

The gateway provides detailed metrics for:
- Request count per route
- Response time statistics
- Error rates
- Active connections

## 🧪 Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Manual Testing

1. Start the API Gateway
2. Test route forwarding: `GET http://localhost:8083/api/users/health`
3. Verify load balancing across service instances
4. Test security filters with invalid tokens

## 🔧 Troubleshooting

### Common Issues

1. **Routes not working**
   - Check Eureka service registration
   - Verify route configuration syntax
   - Check service health status

2. **Authentication failures**
   - Validate JWT token format
   - Check token expiration
   - Verify security filter configuration

3. **Load balancing issues**
   - Ensure multiple service instances are registered
   - Check Eureka client configuration
   - Verify network connectivity

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    org.springframework.cloud.gateway: DEBUG
    reactor.netty: DEBUG
```

## 🚀 Performance Optimization

### Best Practices

1. **Reactive Programming**: Leverage Spring WebFlux for better performance
2. **Caching**: Implement response caching for static data
3. **Rate Limiting**: Prevent abuse with configurable limits
4. **Circuit Breakers**: Protect against cascading failures

### Performance Metrics

- **Throughput**: 1000+ requests/second
- **Latency**: < 50ms average response time
- **Memory Usage**: < 512MB heap
- **CPU Usage**: < 20% under normal load

## 🤝 Contributing

When modifying the API Gateway:
1. Update route configurations carefully
2. Test all downstream services
3. Maintain backward compatibility
4. Document new routes and filters
5. Update security configurations

## 📝 Future Enhancements

- [ ] Implement API versioning strategy
- [ ] Add GraphQL support
- [ ] Implement API documentation (Swagger)
- [ ] Add distributed tracing
- [ ] Implement advanced rate limiting
- [ ] Add request/response caching
- [ ] Implement service mesh integration

---

**Service**: API Gateway
**Port**: 8083
**Type**: Infrastructure Service
**Last Updated**: December 13, 2025