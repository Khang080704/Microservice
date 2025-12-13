# Inventory Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![OpenFeign](https://img.shields.io/badge/OpenFeign-4.1.0-blue)](https://spring.io/projects/spring-cloud-openfeign)

The Inventory Service manages product stock levels, warehouse operations, and inventory tracking for the e-commerce platform. It provides real-time inventory validation and integrates with cart and order services.

## 🎯 Overview

The Inventory Service is the central component for inventory management with comprehensive stock tracking and warehouse operations:

- **Stock Management**: Real-time inventory tracking and updates
- **Warehouse Operations**: Multi-warehouse support with location tracking
- **Stock Validation**: Inventory checks for cart and order operations
- **Low Stock Alerts**: Automatic notifications for inventory thresholds
- **Inventory History**: Complete audit trail of inventory changes
- **Reservation System**: Stock reservation during checkout process

## 🚀 Features

- **Real-time Inventory**: Live stock level monitoring and updates
- **Multi-warehouse Support**: Manage inventory across multiple locations
- **Stock Reservations**: Reserve items during checkout process
- **Low Stock Alerts**: Automated notifications for inventory thresholds
- **Inventory Analytics**: Stock movement analysis and reporting
- **Batch Operations**: Bulk inventory updates and adjustments
- **Audit Trail**: Complete history of all inventory changes

## 🛠️ Technology Stack

- **Spring Boot 4.0.0**: Latest framework for building the application
- **Spring Data JPA**: Data access layer with Hibernate
- **PostgreSQL**: Robust relational database for inventory data
- **Spring Security 6.3.0**: Authentication and authorization
- **Spring Cloud OpenFeign**: Declarative REST client for service communication
- **Lombok**: Code generation for boilerplate code
- **Spring Cloud Netflix Eureka Client**: Service discovery
- **Spring Boot Actuator**: Health monitoring and metrics

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Docker (for containerized deployment)
- Running Product Service and Cart Service

## 🚀 Quick Start

### Local Development

1. **Set up PostgreSQL database**
   ```sql
   CREATE DATABASE inventory_service;
   CREATE USER khang WITH PASSWORD '123456';
   GRANT ALL PRIVILEGES ON DATABASE inventory_service TO khang;
   ```

2. **Configure environment variables**
   Create `.env` file in inventory-service directory:
   ```env
   SPRING_PROFILES_ACTIVE=dev
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/inventory_service
   SPRING_DATASOURCE_USERNAME=khang
   SPRING_DATASOURCE_PASSWORD=123456
   PRODUCT_SERVICE_URL=http://localhost:3000
   CART_SERVICE_URL=http://localhost:3001
   DISCOVERY_SERVER=http://localhost:8761/eureka/
   ```

3. **Navigate to inventory-service directory**
   ```bash
   cd inventory-service
   ```

4. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

5. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/inventory-service-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/inventory-service:latest .

# Run the container
docker run -p 3004:3004 \
  --env-file .env \
  khang080704/inventory-service:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up inventory-service
```

## 🔧 Configuration

### Application Properties

The service supports multiple profiles:

#### Development Profile (`application-dev.yml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/inventory_service
    username: ${DB_USERNAME:khang}
    password: ${DB_PASSWORD:123456}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

feign:
  client:
    config:
      product-service:
        url: ${PRODUCT_SERVICE_URL:http://localhost:3000}
      cart-service:
        url: ${CART_SERVICE_URL:http://localhost:3001}

inventory:
  low-stock-threshold: 10
  reservation-timeout: 30  # minutes

eureka:
  client:
    serviceUrl:
      defaultZone: ${DISCOVERY_SERVER:http://localhost:8761/eureka/}
```

#### Production Profile (`application-prod.yml`)
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

inventory:
  low-stock-threshold: 5
  reservation-timeout: 15
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/inventory_service` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `khang` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `123456` |
| `PRODUCT_SERVICE_URL` | Product service URL | `http://localhost:3000` |
| `CART_SERVICE_URL` | Cart service URL | `http://localhost:3001` |
| `DISCOVERY_SERVER` | Eureka server URL | `http://localhost:8761/eureka/` |

## 📚 API Endpoints

### Inventory Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/inventory` | Get all inventory items | ✅ (Admin) |
| `GET` | `/api/inventory/{productId}` | Get inventory for specific product | ✅ |
| `POST` | `/api/inventory` | Create inventory record | ✅ (Admin) |
| `PUT` | `/api/inventory/{productId}` | Update inventory levels | ✅ (Admin) |
| `DELETE` | `/api/inventory/{productId}` | Remove inventory record | ✅ (Admin) |

### Stock Operations Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/inventory/{productId}/reserve` | Reserve stock for order | ✅ |
| `POST` | `/api/inventory/{productId}/release` | Release reserved stock | ✅ |
| `POST` | `/api/inventory/{productId}/adjust` | Adjust stock levels | ✅ (Admin) |
| `GET` | `/api/inventory/low-stock` | Get low stock alerts | ✅ (Admin) |

### Warehouse Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/warehouses` | Get all warehouses | ✅ (Admin) |
| `POST` | `/api/warehouses` | Create new warehouse | ✅ (Admin) |
| `GET` | `/api/warehouses/{id}/inventory` | Get warehouse inventory | ✅ (Admin) |

### Request/Response Examples

#### Check Product Inventory
```bash
GET /api/inventory/{productId}
Authorization: Bearer {jwt-token}
```

Response:
```json
{
  "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
  "productName": "Wireless Bluetooth Headphones",
  "totalStock": 150,
  "availableStock": 145,
  "reservedStock": 5,
  "warehouses": [
    {
      "warehouseId": "WH001",
      "warehouseName": "Main Warehouse",
      "stock": 100,
      "location": "New York, NY"
    },
    {
      "warehouseId": "WH002",
      "warehouseName": "East Coast DC",
      "stock": 50,
      "location": "Atlanta, GA"
    }
  ],
  "lowStockThreshold": 10,
  "lastUpdated": "2025-12-13T10:30:00Z"
}
```

#### Reserve Stock for Order
```bash
POST /api/inventory/{productId}/reserve
Content-Type: application/json
Authorization: Bearer {jwt-token}

{
  "quantity": 2,
  "orderId": "ORD-2025-001",
  "reservationTimeout": 30
}
```

Response:
```json
{
  "reservationId": "RSV-2025-001-001",
  "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
  "quantity": 2,
  "orderId": "ORD-2025-001",
  "expiresAt": "2025-12-13T11:00:00Z",
  "status": "RESERVED"
}
```

#### Update Inventory Levels
```bash
PUT /api/inventory/{productId}
Content-Type: application/json
Authorization: Bearer {admin-jwt-token}

{
  "warehouseId": "WH001",
  "quantity": 50,
  "operation": "ADD",
  "reason": "New stock arrival",
  "reference": "PO-2025-001"
}
```

## 🔗 Service Integrations

### Product Service Integration

The inventory service communicates with the product service to:
- **Product Validation**: Ensure products exist before inventory operations
- **Product Information**: Get product details for inventory records
- **Stock Synchronization**: Update product availability status

### Cart Service Integration

The inventory service integrates with cart to:
- **Stock Validation**: Check availability before adding items to cart
- **Real-time Updates**: Update cart item availability
- **Reservation Management**: Handle stock reservations during checkout

### Order Service Integration (Planned)

Future integration with order service for:
- **Stock Deduction**: Reduce inventory when orders are fulfilled
- **Reservation Confirmation**: Convert reservations to actual stock reductions
- **Backorder Management**: Handle out-of-stock scenarios

## 📊 Database Schema

### Products Table
```sql
CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Warehouses Table
```sql
CREATE TABLE warehouses (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Inventory Table
```sql
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(36) REFERENCES products(id),
    warehouse_id VARCHAR(10) REFERENCES warehouses(id),
    quantity INTEGER NOT NULL DEFAULT 0,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    low_stock_threshold INTEGER DEFAULT 10,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, warehouse_id)
);
```

### Stock Movements Table
```sql
CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(36) REFERENCES products(id),
    warehouse_id VARCHAR(10) REFERENCES warehouses(id),
    quantity INTEGER NOT NULL,
    operation VARCHAR(20) NOT NULL, -- ADD, REMOVE, RESERVE, RELEASE
    reason VARCHAR(255),
    reference VARCHAR(100), -- Order ID, PO number, etc.
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Stock Reservations Table
```sql
CREATE TABLE stock_reservations (
    id VARCHAR(20) PRIMARY KEY,
    product_id VARCHAR(36) REFERENCES products(id),
    warehouse_id VARCHAR(10) REFERENCES warehouses(id),
    quantity INTEGER NOT NULL,
    order_id VARCHAR(50),
    expires_at TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔄 Inventory Operations Flow

### Stock Management Process

1. **Stock Receipt**: New inventory arrives → Update inventory levels → Log movement
2. **Stock Reservation**: Order placed → Reserve stock → Set expiration timer
3. **Stock Confirmation**: Order fulfilled → Convert reservation to deduction → Update inventory
4. **Stock Release**: Reservation expires → Release reserved stock → Update availability

### Reservation Lifecycle

1. **CREATED**: Reservation initiated
2. **ACTIVE**: Stock successfully reserved
3. **EXPIRED**: Reservation timeout reached
4. **CONFIRMED**: Reservation converted to actual sale
5. **CANCELLED**: Reservation manually cancelled

## 🔒 Security

### Authentication & Authorization

- **JWT Token Validation**: Secure service identification
- **Role-based Access**: Admin privileges for inventory management
- **API Rate Limiting**: Prevent abuse of inventory operations
- **Audit Logging**: Track all inventory modifications

### Data Protection

- **Transaction Management**: ACID compliance for inventory operations
- **Concurrent Access**: Optimistic locking for inventory updates
- **Data Validation**: Comprehensive validation for all inventory changes

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:3004/actuator/health
- **Info**: http://localhost:3004/actuator/info
- **Metrics**: http://localhost:3004/actuator/metrics

### Key Metrics

- Inventory level monitoring
- Reservation success/failure rates
- Stock movement tracking
- Low stock alert generation
- Database transaction performance

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

- **Inventory CRUD Tests**: Create, read, update, delete inventory operations
- **Reservation Tests**: Stock reservation and release operations
- **Warehouse Tests**: Multi-warehouse inventory management
- **Service Integration Tests**: Product and cart service communication
- **Concurrency Tests**: Concurrent inventory operations

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

1. **Stock Inconsistency**
   - Check concurrent transaction handling
   - Verify reservation cleanup process
   - Review stock movement logs

2. **Reservation Timeouts**
   - Check reservation expiration job
   - Verify database cleanup processes
   - Monitor system clock synchronization

3. **Service Communication Errors**
   - Check service discovery registration
   - Verify network connectivity
   - Review Feign client configuration

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    org.springframework.orm.jpa: DEBUG
    org.springframework.transaction: DEBUG
    com.example.inventoryservice: DEBUG
```

## 🚀 Performance Optimization

### Database Optimization

- **Indexes**: Optimized indexes on product_id, warehouse_id
- **Partitioning**: Time-based partitioning for stock movements
- **Connection Pooling**: HikariCP for efficient connection management
- **Query Optimization**: Efficient queries with proper indexing

### Caching Strategy

- **Inventory Cache**: Cache frequently accessed inventory data
- **Product Cache**: Cache product information with short TTL
- **Reservation Cache**: Cache active reservations

### Concurrent Access Management

- **Optimistic Locking**: Prevent concurrent modification conflicts
- **Database Transactions**: Ensure data consistency
- **Queue-based Updates**: Asynchronous inventory updates for high volume

## 🤝 Contributing

When modifying the Inventory Service:
1. Maintain data consistency across all inventory operations
2. Update service integration contracts carefully
3. Add comprehensive tests for concurrent scenarios
4. Document database schema changes
5. Follow ACID principles for inventory transactions

## 📝 Future Enhancements

- [ ] Advanced analytics and reporting dashboard
- [ ] Automated reorder point calculations
- [ ] Integration with external ERP systems
- [ ] Real-time inventory synchronization across warehouses
- [ ] Advanced forecasting and demand planning
- [ ] Mobile app for warehouse staff
- [ ] RFID integration for automated inventory tracking
- [ ] Multi-channel inventory management

---

**Service**: Inventory Service
**Port**: 3004
**Database**: PostgreSQL
**Type**: Business Service
**Last Updated**: December 13, 2025