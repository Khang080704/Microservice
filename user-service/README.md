# User Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.3.0-red)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-0.11.5-blue)](https://github.com/jwtk/jjwt)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

The User Service manages user accounts, authentication, and authorization for the e-commerce platform. It provides secure user registration, login, profile management, and role-based access control.

## 🎯 Overview

The User Service is responsible for all user-related operations in the microservices architecture:

- **User Management**: Registration, profile updates, account management
- **Authentication**: JWT-based login and token validation
- **Authorization**: Role-based access control (RBAC)
- **Security**: Password encryption, secure token handling
- **Caching**: Redis integration for session management

## 🚀 Features

- **User Registration**: Secure user account creation with validation
- **Authentication**: JWT token-based authentication system
- **User Profiles**: Comprehensive user profile management
- **Role Management**: Admin, User, and custom roles support
- **Password Security**: BCrypt password encryption
- **Session Management**: Redis-based session caching
- **Email Integration**: User notification system (planned)

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Security 6.3.0**: Authentication and authorization framework
- **Spring Data JPA**: Data access layer with Hibernate
- **PostgreSQL**: Primary database for user data
- **Redis**: Session caching and temporary data storage
- **JWT (JJWT 0.11.5)**: JSON Web Token implementation
- **ModelMapper**: Object mapping between DTOs and entities
- **Lombok**: Code generation for boilerplate code
- **Spring Cloud Netflix Eureka Client**: Service discovery

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Redis server (optional, for caching)
- Docker (for containerized deployment)

## 🚀 Quick Start

### Local Development

1. **Set up PostgreSQL database**
   ```sql
   CREATE DATABASE user_service;
   CREATE USER khang WITH PASSWORD '123456';
   GRANT ALL PRIVILEGES ON DATABASE user_service TO khang;
   ```

2. **Configure environment variables**
   Create `.env` file in user-service directory:
   ```env
   SPRING_PROFILES_ACTIVE=dev
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/user_service
   SPRING_DATASOURCE_USERNAME=khang
   SPRING_DATASOURCE_PASSWORD=123456
   JWT_SECRET=your-jwt-secret-key-here
   REDIS_HOST=localhost
   REDIS_PORT=6379
   DISCOVERY_SERVER=http://localhost:8761/eureka/
   ```

3. **Navigate to user-service directory**
   ```bash
   cd user-service
   ```

4. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

5. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/user-service-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/user-service:latest .

# Run the container
docker run -p 8081:8081 \
  --env-file .env \
  khang080704/user-service:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up user-service
```

## 🔧 Configuration

### Application Properties

The service supports multiple profiles:

#### Development Profile (`application-dev.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_service
    username: ${DB_USERNAME:khang}
    password: ${DB_PASSWORD:123456}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}

jwt:
  secret: ${JWT_SECRET:your-secret-key}
  expiration: 86400000  # 24 hours

eureka:
  client:
    serviceUrl:
      defaultZone: ${DISCOVERY_SERVER:http://localhost:8761/eureka/}
```

#### Production Profile (`application-prod.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://prod-db:5432/user_service
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  redis:
    host: ${REDIS_HOST}
    port: ${REDIS_PORT}

jwt:
  secret: ${JWT_SECRET}
  expiration: 3600000  # 1 hour

logging:
  level:
    com.example: INFO
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/user_service` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `khang` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `123456` |
| `JWT_SECRET` | JWT signing secret | Required |
| `JWT_EXPIRATION` | JWT token expiration (ms) | `86400000` |
| `REDIS_HOST` | Redis server host | `localhost` |
| `REDIS_PORT` | Redis server port | `6379` |
| `DISCOVERY_SERVER` | Eureka server URL | `http://localhost:8761/eureka/` |

## 📚 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | User registration |
| `POST` | `/api/auth/login` | User login |
| `POST` | `/api/auth/refresh` | Refresh JWT token |
| `POST` | `/api/auth/logout` | User logout |

### User Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/users/me` | Get current user profile | ✅ |
| `PUT` | `/api/users/me` | Update current user profile | ✅ |
| `DELETE` | `/api/users/me` | Delete current user account | ✅ |
| `GET` | `/api/users/{id}` | Get user by ID (Admin only) | ✅ |
| `GET` | `/api/users` | Get all users (Admin only) | ✅ |
| `PUT` | `/api/users/{id}` | Update user (Admin only) | ✅ |
| `DELETE` | `/api/users/{id}` | Delete user (Admin only) | ✅ |

### Request/Response Examples

#### User Registration
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### User Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "securePassword123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "roles": ["USER"]
  }
}
```

## 🔒 Security

### Authentication Flow

1. **Registration**: User provides credentials, password is hashed with BCrypt
2. **Login**: Credentials validated, JWT access and refresh tokens generated
3. **Token Validation**: Each request validates JWT token in Authorization header
4. **Refresh**: Access token refreshed using refresh token before expiration

### Authorization

- **USER**: Basic user operations (profile management)
- **ADMIN**: Full access to all user management operations
- **MODERATOR**: Limited administrative access

### Password Security

- **BCrypt**: Industry-standard password hashing
- **Salt**: Automatic salt generation for each password
- **Strength Requirements**: Minimum 8 characters, mixed case, numbers, symbols

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Roles Table
```sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
```

### User Roles Table
```sql
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);
```

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:8081/actuator/health
- **Info**: http://localhost:8081/actuator/info
- **Metrics**: http://localhost:8081/actuator/metrics

### Key Metrics

- User registration count
- Login success/failure rates
- Token refresh frequency
- Database connection pool status
- Redis cache hit/miss ratios

## 🧪 Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Test Categories

- **Authentication Tests**: JWT token generation and validation
- **User Management Tests**: CRUD operations on user entities
- **Security Tests**: Authorization and access control
- **Database Tests**: Repository layer testing with H2

### Test Database

Tests use H2 in-memory database for isolation:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
```

## 🔧 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify PostgreSQL is running
   - Check connection credentials
   - Ensure database exists

2. **JWT Token Invalid**
   - Check JWT secret key configuration
   - Verify token expiration time
   - Ensure proper token format

3. **Redis Connection Failed**
   - Verify Redis server is running
   - Check Redis host/port configuration
   - Ensure network connectivity

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    com.example.userservice: DEBUG
    org.springframework.security: DEBUG
```

## 🚀 Performance Optimization

### Caching Strategy

- **User Sessions**: Redis caching for active sessions
- **User Profiles**: Cache frequently accessed user data
- **Token Blacklist**: Redis for invalidated tokens

### Database Optimization

- **Indexes**: Optimized indexes on username, email fields
- **Connection Pooling**: HikariCP for efficient connection management
- **Query Optimization**: Efficient JPA queries with proper fetching

## 🤝 Contributing

When modifying the User Service:
1. Maintain backward compatibility for APIs
2. Update security configurations carefully
3. Add comprehensive tests for new features
4. Document security implications
5. Follow JWT and OAuth best practices

## 📝 Future Enhancements

- [ ] Email verification for registration
- [ ] Password reset functionality
- [ ] Two-factor authentication (2FA)
- [ ] Social login integration
- [ ] User activity logging and audit trails
- [ ] Advanced user profile customization
- [ ] API rate limiting per user
- [ ] User preferences and settings

---

**Service**: User Service
**Port**: 8081
**Database**: PostgreSQL
**Cache**: Redis
**Type**: Business Service
**Last Updated**: December 13, 2025

