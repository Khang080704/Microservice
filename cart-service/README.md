# Cart Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green)](https://www.mongodb.com/)
[![OpenFeign](https://img.shields.io/badge/OpenFeign-4.1.0-blue)](https://spring.io/projects/spring-cloud-openfeign)

The Cart Service manages shopping cart functionality for the e-commerce platform. It handles cart operations, item management, and integrates with product and inventory services using MongoDB for data persistence.

## 🎯 Overview

The Cart Service provides comprehensive shopping cart management with real-time inventory validation and product information synchronization:

- **Cart Management**: Create, update, and manage shopping carts
- **Item Operations**: Add, remove, update cart items with quantity management
- **Inventory Integration**: Real-time stock validation with inventory service
- **Product Sync**: Automatic product information updates from product service
- **User Sessions**: Persistent cart storage across sessions
- **Price Calculation**: Dynamic pricing with discounts and promotions

## 🚀 Features

- **Persistent Shopping Carts**: Cart persistence across user sessions
- **Real-time Inventory**: Live stock checking before adding items
- **Product Validation**: Automatic product information synchronization
- **Quantity Management**: Smart quantity updates with stock limits
- **Cart Merging**: Merge guest carts with user accounts
- **Bulk Operations**: Add multiple items simultaneously
- **Cart Expiration**: Automatic cleanup of abandoned carts

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Data MongoDB**: MongoDB integration and ODM
- **Spring Security 6.3.0**: Authentication and authorization
- **Spring Cloud OpenFeign**: Declarative REST client for service communication
- **MongoDB**: NoSQL document database for cart storage
- **Lombok**: Code generation for boilerplate code
- **Spring Cloud Netflix Eureka Client**: Service discovery
- **Spring Boot Actuator**: Health monitoring and metrics

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB database
- Docker (for containerized deployment)
- Running Product Service and Inventory Service

## 🚀 Quick Start

### Local Development

1. **Set up MongoDB database**
   ```bash
   # Using Docker
   docker run -d \
     --name mongodb-cart \
     -p 27018:27017 \
     -e MONGO_INITDB_ROOT_USERNAME=admin \
     -e MONGO_INITDB_ROOT_PASSWORD=password \
     mongo:latest
   ```

2. **Configure environment variables**
   Create `.env` file in cart-service directory:
   ```env
   SPRING_PROFILES_ACTIVE=dev
   SPRING_DATA_MONGODB_HOST=localhost
   SPRING_DATA_MONGODB_PORT=27018
   SPRING_DATA_MONGODB_DATABASE=cart_service
   SPRING_DATA_MONGODB_USERNAME=admin
   SPRING_DATA_MONGODB_PASSWORD=password
   PRODUCT_SERVICE_URL=http://localhost:3000
   INVENTORY_SERVICE_URL=http://localhost:3004
   DISCOVERY_SERVER=http://localhost:8761/eureka/
   ```

3. **Navigate to cart-service directory**
   ```bash
   cd cart-service
   ```

4. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

5. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/cart-service-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/cart-service:latest .

# Run the container
docker run -p 3001:3001 \
  --env-file .env \
  khang080704/cart-service:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up cart-service
```

## 🔧 Configuration

### Application Properties

The service supports multiple profiles:

#### Development Profile (`application-dev.yml`)
```yaml
spring:
  data:
    mongodb:
      host: ${MONGODB_HOST:localhost}
      port: ${MONGODB_PORT:27018}
      database: ${MONGODB_DATABASE:cart_service}
      username: ${MONGODB_USERNAME:admin}
      password: ${MONGODB_PASSWORD:password}

feign:
  client:
    config:
      product-service:
        url: ${PRODUCT_SERVICE_URL:http://localhost:3000}
      inventory-service:
        url: ${INVENTORY_SERVICE_URL:http://localhost:3004}

cart:
  expiration:
    hours: 24
  max-items: 50

eureka:
  client:
    serviceUrl:
      defaultZone: ${DISCOVERY_SERVER:http://localhost:8761/eureka/}
```

#### Production Profile (`application-prod.yml`)
```yaml
spring:
  data:
    mongodb:
      host: ${MONGODB_HOST}
      port: ${MONGODB_PORT}
      database: ${MONGODB_DATABASE}
      username: ${MONGODB_USERNAME}
      password: ${MONGODB_PASSWORD}

feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 10000

cart:
  expiration:
    hours: 168  # 7 days
  max-items: 100
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATA_MONGODB_HOST` | MongoDB server host | `localhost` |
| `SPRING_DATA_MONGODB_PORT` | MongoDB server port | `27018` |
| `SPRING_DATA_MONGODB_DATABASE` | MongoDB database name | `cart_service` |
| `SPRING_DATA_MONGODB_USERNAME` | MongoDB username | `admin` |
| `SPRING_DATA_MONGODB_PASSWORD` | MongoDB password | `password` |
| `PRODUCT_SERVICE_URL` | Product service URL | `http://localhost:3000` |
| `INVENTORY_SERVICE_URL` | Inventory service URL | `http://localhost:3004` |
| `DISCOVERY_SERVER` | Eureka server URL | `http://localhost:8761/eureka/` |

## 📚 API Endpoints

### Cart Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/cart` | Get user's shopping cart | ✅ |
| `POST` | `/api/cart` | Create new cart (if none exists) | ✅ |
| `DELETE` | `/api/cart` | Clear entire cart | ✅ |

### Cart Item Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/cart/items` | Add item to cart | ✅ |
| `PUT` | `/api/cart/items/{productId}` | Update item quantity | ✅ |
| `DELETE` | `/api/cart/items/{productId}` | Remove item from cart | ✅ |
| `GET` | `/api/cart/items` | Get all cart items | ✅ |

### Bulk Operations Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/cart/bulk` | Add multiple items to cart | ✅ |
| `PUT` | `/api/cart/merge` | Merge guest cart with user cart | ✅ |

### Request/Response Examples

#### Add Item to Cart
```bash
POST /api/cart/items
Content-Type: application/json
Authorization: Bearer {jwt-token}

{
  "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
  "quantity": 2,
  "attributes": {
    "color": "Black",
    "size": "M"
  }
}
```

Response:
```json
{
  "cartId": "64f1a2b3c4d5e6f7g8h9i0j2",
  "userId": "user123",
  "items": [
    {
      "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
      "productName": "Wireless Bluetooth Headphones",
      "quantity": 2,
      "unitPrice": 199.99,
      "totalPrice": 399.98,
      "attributes": {
        "color": "Black",
        "size": "M"
      },
      "available": true
    }
  ],
  "subtotal": 399.98,
  "tax": 39.99,
  "shipping": 9.99,
  "total": 449.96,
  "itemCount": 2,
  "createdAt": "2025-12-13T10:30:00Z",
  "updatedAt": "2025-12-13T10:30:00Z"
}
```

#### Get Cart Summary
```bash
GET /api/cart
Authorization: Bearer {jwt-token}
```

Response:
```json
{
  "cartId": "64f1a2b3c4d5e6f7g8h9i0j2",
  "userId": "user123",
  "items": [
    {
      "productId": "64f1a2b3c4d5e6f7g8h9i0j1",
      "productName": "Wireless Headphones",
      "imageUrl": "https://example.com/images/headphones.jpg",
      "quantity": 2,
      "unitPrice": 199.99,
      "totalPrice": 399.98,
      "available": true
    }
  ],
  "summary": {
    "subtotal": 399.98,
    "discount": 20.00,
    "tax": 38.00,
    "shipping": 9.99,
    "total": 427.97
  },
  "valid": true,
  "expiresAt": "2025-12-14T10:30:00Z"
}
```

## 🔗 Service Integrations

### Product Service Integration

The cart service communicates with the product service to:
- **Validate Product Existence**: Ensure products exist before adding to cart
- **Retrieve Product Details**: Get current product information and pricing
- **Sync Product Updates**: Update cart items when product information changes

### Inventory Service Integration

The cart service integrates with inventory to:
- **Stock Validation**: Check available quantity before adding items
- **Real-time Updates**: Update cart availability based on inventory changes
- **Reservation Management**: Reserve items during checkout process

## 📊 Database Schema

### Carts Collection
```javascript
{
  _id: ObjectId("64f1a2b3c4d5e6f7g8h9i0j2"),
  userId: "user123",
  guestId: null,
  status: "ACTIVE",
  items: [
    {
      productId: "64f1a2b3c4d5e6f7g8h9i0j1",
      productName: "Wireless Bluetooth Headphones",
      imageUrl: "https://example.com/images/headphones.jpg",
      quantity: 2,
      unitPrice: 199.99,
      totalPrice: 399.98,
      attributes: {
        color: "Black",
        size: "M"
      },
      addedAt: ISODate("2025-12-13T10:30:00Z")
    }
  ],
  pricing: {
    subtotal: 399.98,
    discount: 20.00,
    tax: 38.00,
    shipping: 9.99,
    total: 427.97
  },
  createdAt: ISODate("2025-12-13T10:30:00Z"),
  updatedAt: ISODate("2025-12-13T10:30:00Z"),
  expiresAt: ISODate("2025-12-14T10:30:00Z")
}
```

## 🔄 Cart Lifecycle

### Cart States

1. **CREATED**: Initial cart creation
2. **ACTIVE**: Cart is being used by customer
3. **CHECKOUT**: Cart is in checkout process
4. **COMPLETED**: Cart has been converted to order
5. **ABANDONED**: Cart expired or was abandoned
6. **EXPIRED**: Cart automatically expired

### Cart Operations Flow

1. **Add Item**: Validate product → Check inventory → Add/Update item → Recalculate totals
2. **Update Quantity**: Validate stock → Update quantity → Recalculate totals
3. **Remove Item**: Remove item → Recalculate totals
4. **Checkout**: Validate all items → Reserve inventory → Create order

## 🔒 Security

### Authentication & Authorization

- **JWT Token Validation**: Secure user identification
- **User Isolation**: Users can only access their own carts
- **Input Validation**: Comprehensive validation for all cart operations
- **Rate Limiting**: Prevent abuse of cart operations

### Data Protection

- **Secure Communication**: Encrypted service-to-service communication
- **Audit Logging**: Track all cart modifications
- **Data Sanitization**: Prevent injection attacks

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:3001/actuator/health
- **Info**: http://localhost:3001/actuator/info
- **Metrics**: http://localhost:3001/actuator/metrics

### Key Metrics

- Cart creation/update rates
- Service integration response times
- Inventory validation success rates
- Cart expiration cleanup statistics
- Error rates by operation type

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

- **Cart CRUD Tests**: Create, read, update, delete cart operations
- **Item Management Tests**: Add, update, remove cart items
- **Service Integration Tests**: Product and inventory service communication
- **Security Tests**: Authentication and authorization
- **Performance Tests**: Concurrent cart operations

### Test Database

Tests use embedded MongoDB for isolation:
```yaml
spring:
  mongodb:
    embedded:
      version: 4.4.1
```

## 🔧 Troubleshooting

### Common Issues

1. **Product Service Unavailable**
   - Check product service health
   - Verify service discovery configuration
   - Review network connectivity

2. **Inventory Validation Failed**
   - Ensure inventory service is running
   - Check inventory data consistency
   - Verify service communication

3. **Cart Persistence Issues**
   - Check MongoDB connection
   - Verify database permissions
   - Review connection pool settings

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    org.springframework.data.mongodb: DEBUG
    org.springframework.cloud.openfeign: DEBUG
    com.example.cartservice: DEBUG
```

## 🚀 Performance Optimization

### Database Optimization

- **Indexes**: Optimized indexes on userId and productId
- **Compound Indexes**: Multi-field indexes for common queries
- **TTL Indexes**: Automatic expiration of abandoned carts

### Caching Strategy

- **Product Cache**: Cache frequently accessed product information
- **Inventory Cache**: Cache inventory status with short TTL
- **Cart Cache**: Cache active cart data in Redis

### Query Optimization

- **Aggregation Pipelines**: Efficient cart total calculations
- **Projection**: Return only required fields
- **Batch Operations**: Bulk cart updates where possible

## 🤝 Contributing

When modifying the Cart Service:
1. Maintain backward compatibility for cart APIs
2. Update service integration contracts carefully
3. Add comprehensive tests for cart operations
4. Document performance implications
5. Follow MongoDB and microservices best practices

## 📝 Future Enhancements

- [ ] Cart sharing and collaboration features
- [ ] Advanced cart analytics and insights
- [ ] Wishlist functionality integration
- [ ] Cart recommendation engine
- [ ] Multi-currency support
- [ ] Cart backup and recovery
- [ ] Real-time cart synchronization across devices
- [ ] Advanced discount and coupon system

---

**Service**: Cart Service
**Port**: 3001
**Database**: MongoDB
**Type**: Business Service
**Last Updated**: December 13, 2025