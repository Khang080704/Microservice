# Order Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.6.0-orange)](https://kafka.apache.org/)
[![JWT](https://img.shields.io/badge/JWT-0.11.5-blue)](https://github.com/jwtk/jjwt)

The Order Service manages the complete order lifecycle for the e-commerce platform. It handles order creation, payment processing, fulfillment, and integrates with inventory and notification services using event-driven architecture with Apache Kafka.

## 🎯 Overview

The Order Service is the orchestration component for order management with comprehensive order processing and event-driven communication:

- **Order Lifecycle Management**: Complete order processing from creation to delivery
- **Payment Integration**: Secure payment processing and transaction management
- **Inventory Coordination**: Real-time inventory updates and stock management
- **Event-Driven Architecture**: Asynchronous communication using Apache Kafka
- **Order Tracking**: Real-time order status updates and history
- **Notification System**: Automated customer and admin notifications
- **Fraud Detection**: Basic fraud prevention and risk assessment

## 🚀 Features

- **Order Creation**: Multi-step order creation with validation
- **Payment Processing**: Integration with payment gateways
- **Order Fulfillment**: Automated order processing and shipping
- **Status Tracking**: Real-time order status updates
- **Event Streaming**: Kafka-based event-driven architecture
- **Inventory Sync**: Automatic inventory deduction and updates
- **Notification System**: Email and SMS notifications
- **Order History**: Complete audit trail of order changes

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Data JPA**: Data access layer with Hibernate
- **MySQL**: Relational database for order data persistence
- **Apache Kafka**: Event streaming platform for asynchronous communication
- **Spring Kafka**: Kafka integration for producers and consumers
- **Spring Security 6.3.0**: Authentication and authorization
- **Spring Cloud OpenFeign**: Declarative REST client for service communication
- **JWT (JJWT 0.11.5)**: JSON Web Token implementation
- **Lombok**: Code generation for boilerplate code
- **Spring Cloud Netflix Eureka Client**: Service discovery

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL database
- Apache Kafka (or Docker)
- Docker (for containerized deployment)
- Running Inventory Service and Notification Service

## 🚀 Quick Start

### Local Development

1. **Set up MySQL database**
   ```sql
   CREATE DATABASE order_service;
   CREATE USER khang IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON order_service.* TO khang;
   ```

2. **Start Kafka (using Docker)**
   ```bash
   # From kafka-docker directory
   cd kafka-docker
   docker-compose up -d
   ```

3. **Configure environment variables**
   Create `.env` file in order-service directory:
   ```env
   SPRING_PROFILES_ACTIVE=dev
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/order_service
   SPRING_DATASOURCE_USERNAME=khang
   SPRING_DATASOURCE_PASSWORD=password
   SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
   INVENTORY_SERVICE_URL=http://localhost:3004
   NOTIFICATION_SERVICE_URL=http://localhost:3005
   PAYMENT_GATEWAY_URL=https://api.payment-gateway.com
   DISCOVERY_SERVER=http://localhost:8761/eureka/
   ```

4. **Navigate to order-service directory**
   ```bash
   cd order-service
   ```

5. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

6. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/order-service-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/order-service:latest .

# Run the container
docker run -p 8082:8082 \
  --env-file .env \
  khang080704/order-service:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up order-service
```

## 🔧 Configuration

### Application Properties

The service supports multiple profiles:

#### Development Profile (`application-dev.yml`)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/order_service
    username: ${DB_USERNAME:khang}
    password: ${DB_PASSWORD:password}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: order-service
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.example.orderservice.events

feign:
  client:
    config:
      inventory-service:
        url: ${INVENTORY_SERVICE_URL:http://localhost:3004}
      notification-service:
        url: ${NOTIFICATION_SERVICE_URL:http://localhost:3005}

order:
  payment-timeout: 30  # minutes
  auto-cancel: 24      # hours

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

order:
  payment-timeout: 15
  auto-cancel: 48
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATASOURCE_URL` | MySQL connection URL | `jdbc:mysql://localhost:3306/order_service` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `khang` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `password` |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka bootstrap servers | `localhost:9092` |
| `INVENTORY_SERVICE_URL` | Inventory service URL | `http://localhost:3004` |
| `NOTIFICATION_SERVICE_URL` | Notification service URL | `http://localhost:3005` |
| `PAYMENT_GATEWAY_URL` | Payment gateway URL | Required |
| `DISCOVERY_SERVER` | Eureka server URL | `http://localhost:8761/eureka/` |

## 📚 API Endpoints

### Order Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/orders` | Create new order | ✅ |
| `GET` | `/api/orders` | Get user's orders | ✅ |
| `GET` | `/api/orders/{id}` | Get order by ID | ✅ |
| `PUT` | `/api/orders/{id}/cancel` | Cancel order | ✅ |
| `GET` | `/api/orders/{id}/status` | Get order status | ✅ |

### Admin Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/admin/orders` | Get all orders (Admin) | ✅ (Admin) |
| `PUT` | `/api/admin/orders/{id}/status` | Update order status (Admin) | ✅ (Admin) |
| `GET` | `/api/admin/orders/stats` | Get order statistics (Admin) | ✅ (Admin) |

### Payment Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/orders/{id}/payment` | Process payment | ✅ |
| `GET` | `/api/orders/{id}/payment/status` | Get payment status | ✅ |
| `POST` | `/api/orders/{id}/payment/webhook` | Payment webhook | ❌ |

### Request/Response Examples

#### Create Order
```bash
POST /api/orders
Content-Type: application/json
Authorization: Bearer {jwt-token}

{
  "cartId": "64f1a2b3c4d5e6f7g8h9i0j2",
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA"
  },
  "billingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA"
  },
  "paymentMethod": {
    "type": "CREDIT_CARD",
    "cardNumber": "4111111111111111",
    "expiryMonth": 12,
    "expiryYear": 2025,
    "cvv": "123"
  }
}
```

Response:
```json
{
  "orderId": "ORD-2025-001",
  "status": "PENDING_PAYMENT",
  "totalAmount": 427.97,
  "currency": "USD",
  "items": [
    {
      "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
      "productName": "Wireless Headphones",
      "quantity": 2,
      "unitPrice": 199.99,
      "totalPrice": 399.98
    }
  ],
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA"
  },
  "createdAt": "2025-12-13T11:00:00Z",
  "expiresAt": "2025-12-13T11:30:00Z"
}
```

#### Get Order Status
```bash
GET /api/orders/{orderId}/status
Authorization: Bearer {jwt-token}
```

Response:
```json
{
  "orderId": "ORD-2025-001",
  "status": "CONFIRMED",
  "statusHistory": [
    {
      "status": "PENDING_PAYMENT",
      "timestamp": "2025-12-13T11:00:00Z",
      "message": "Order created, awaiting payment"
    },
    {
      "status": "PAYMENT_PROCESSING",
      "timestamp": "2025-12-13T11:05:00Z",
      "message": "Payment processing initiated"
    },
    {
      "status": "CONFIRMED",
      "timestamp": "2025-12-13T11:10:00Z",
      "message": "Payment confirmed, order processing"
    }
  ],
  "estimatedDelivery": "2025-12-16T10:00:00Z"
}
```

## 🔄 Order Processing Flow

### Order Lifecycle

1. **CREATED**: Order initialized from cart
2. **PENDING_PAYMENT**: Awaiting payment confirmation
3. **PAYMENT_PROCESSING**: Payment being processed
4. **CONFIRMED**: Payment successful, order confirmed
5. **PROCESSING**: Order being prepared for shipment
6. **SHIPPED**: Order shipped to customer
7. **DELIVERED**: Order delivered successfully
8. **CANCELLED**: Order cancelled by customer or admin
9. **REFUNDED**: Order refunded

### Event-Driven Processing

The service uses Kafka for asynchronous event processing:

#### Produced Events
- `order.created`: New order created
- `order.payment.initiated`: Payment processing started
- `order.payment.completed`: Payment successful
- `order.confirmed`: Order confirmed and processing
- `order.shipped`: Order shipped
- `order.delivered`: Order delivered
- `order.cancelled`: Order cancelled

#### Consumed Events
- `inventory.reserved`: Inventory reservation confirmed
- `inventory.released`: Inventory reservation released
- `payment.failed`: Payment processing failed
- `shipping.updated`: Shipping status updated

## 🔗 Service Integrations

### Inventory Service Integration

The order service communicates with inventory to:
- **Stock Validation**: Verify product availability before order confirmation
- **Stock Reservation**: Reserve items during checkout process
- **Stock Deduction**: Reduce inventory when orders are fulfilled
- **Stock Release**: Release reserved stock when orders are cancelled

### Notification Service Integration

The order service integrates with notifications for:
- **Order Confirmations**: Send order confirmation emails
- **Status Updates**: Notify customers of order status changes
- **Shipping Notifications**: Send tracking information
- **Payment Confirmations**: Confirm successful payments

### Payment Gateway Integration

External payment processing for:
- **Credit Card Processing**: Secure payment processing
- **Payment Status**: Real-time payment status updates
- **Webhook Handling**: Asynchronous payment confirmations
- **Refund Processing**: Payment refunds for cancellations

## 📊 Database Schema

### Orders Table
```sql
CREATE TABLE orders (
    id VARCHAR(20) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    status ENUM('CREATED', 'PENDING_PAYMENT', 'PAYMENT_PROCESSING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'REFUNDED') NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    shipping_address JSON,
    billing_address JSON,
    payment_method JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);
```

### Order Items Table
```sql
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    product_attributes JSON,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
);
```

### Order Status History Table
```sql
CREATE TABLE order_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL,
    message TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    INDEX idx_order_id (order_id)
);
```

### Payments Table
```sql
CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'USD',
    status ENUM('PENDING', 'PROCESSING', 'COMPLETED', 'FAILED', 'REFUNDED') NOT NULL,
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    gateway_response JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    INDEX idx_order_id (order_id),
    INDEX idx_status (status)
);
```

## 🔒 Security

### Authentication & Authorization

- **JWT Token Validation**: Secure user identification
- **Order Ownership**: Users can only access their own orders
- **Admin Access**: Administrative endpoints for order management
- **Payment Security**: Secure payment data handling

### Data Protection

- **Transaction Management**: ACID compliance for order operations
- **Audit Logging**: Complete audit trail of order changes
- **Payment Data Security**: PCI DSS compliant payment handling

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:8082/actuator/health
- **Info**: http://localhost:8082/actuator/info
- **Metrics**: http://localhost:8082/actuator/metrics

### Key Metrics

- Order creation and completion rates
- Payment success/failure rates
- Order processing time statistics
- Kafka message processing metrics
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

- **Order CRUD Tests**: Create, read, update order operations
- **Payment Tests**: Payment processing and validation
- **Event Processing Tests**: Kafka message handling
- **Service Integration Tests**: Inventory and notification service communication
- **Security Tests**: Authentication and authorization

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

1. **Order Creation Failures**
   - Check inventory availability
   - Verify payment method validity
   - Review order validation rules

2. **Payment Processing Issues**
   - Check payment gateway connectivity
   - Verify webhook endpoint configuration
   - Review payment timeout settings

3. **Kafka Message Processing**
   - Check Kafka broker connectivity
   - Verify topic configurations
   - Review consumer group settings

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    org.springframework.kafka: DEBUG
    org.springframework.orm.jpa: DEBUG
    com.example.orderservice: DEBUG
```

## 🚀 Performance Optimization

### Database Optimization

- **Indexes**: Optimized indexes on order_id, user_id, status
- **Partitioning**: Time-based partitioning for large tables
- **Connection Pooling**: Efficient connection management
- **Query Optimization**: Efficient queries with proper indexing

### Event Processing Optimization

- **Message Batching**: Batch processing for high-throughput scenarios
- **Dead Letter Queue**: Handle failed message processing
- **Retry Mechanisms**: Configurable retry policies for failed operations

### Caching Strategy

- **Order Cache**: Cache frequently accessed order data
- **Product Cache**: Cache product information for order items
- **Payment Cache**: Cache payment status information

## 🤝 Contributing

When modifying the Order Service:
1. Maintain order data consistency across all operations
2. Update event contracts carefully when changing Kafka messages
3. Add comprehensive tests for payment and order flows
4. Document database schema changes
5. Follow event-driven architecture best practices

## 📝 Future Enhancements

- [ ] Advanced fraud detection and prevention
- [ ] Multi-currency and international payment support
- [ ] Advanced order analytics and reporting
- [ ] Integration with shipping providers (FedEx, UPS)
- [ ] Order splitting and partial fulfillment
- [ ] Subscription and recurring order management
- [ ] Advanced return and refund processing
- [ ] Real-time order tracking with GPS integration

---

**Service**: Order Service
**Port**: 8082
**Database**: MySQL
**Message Broker**: Apache Kafka
**Type**: Business Service
**Last Updated**: December 13, 2025