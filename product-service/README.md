# Product Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green)](https://www.mongodb.com/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.3.0-red)](https://spring.io/projects/spring-security)

The Product Service manages the product catalog for the e-commerce platform. It handles product information, categories, inventory tracking, and product search functionality using MongoDB as the data store.

## 🎯 Overview

The Product Service is the central component for managing product-related data in the microservices architecture:

- **Product Catalog**: Complete product information management
- **Category Management**: Hierarchical product categorization
- **Inventory Integration**: Real-time inventory status updates
- **Search & Filtering**: Advanced product search capabilities
- **Product Images**: Image management and optimization

## 🚀 Features

- **Product Management**: Full CRUD operations for products
- **Category Hierarchy**: Multi-level product categorization
- **Advanced Search**: Text search with filters and sorting
- **Inventory Sync**: Integration with inventory service
- **Image Management**: Product image upload and optimization
- **Product Variants**: Support for product variations (size, color, etc.)
- **SEO Optimization**: Meta tags and structured data

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Data MongoDB**: MongoDB integration and ODM
- **Spring Security 6.3.0**: Authentication and authorization
- **MongoDB**: NoSQL document database
- **Lombok**: Code generation for boilerplate code
- **Spring Cloud Netflix Eureka Client**: Service discovery
- **Spring Boot Actuator**: Health monitoring and metrics

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB database
- Docker (for containerized deployment)

## 🚀 Quick Start

### Local Development

1. **Set up MongoDB database**
   ```bash
   # Using Docker
   docker run -d \
     --name mongodb \
     -p 27017:27017 \
     -e MONGO_INITDB_ROOT_USERNAME=admin \
     -e MONGO_INITDB_ROOT_PASSWORD=password \
     mongo:latest
   ```

2. **Configure environment variables**
   Create `.env` file in product-service directory:
   ```env
   SPRING_PROFILES_ACTIVE=dev
   SPRING_DATA_MONGODB_HOST=localhost
   SPRING_DATA_MONGODB_PORT=27017
   SPRING_DATA_MONGODB_DATABASE=product_service
   SPRING_DATA_MONGODB_USERNAME=admin
   SPRING_DATA_MONGODB_PASSWORD=password
   DISCOVERY_SERVER=http://localhost:8761/eureka/
   ```

3. **Navigate to product-service directory**
   ```bash
   cd product-service
   ```

4. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

5. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/product-service-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/product-service:latest .

# Run the container
docker run -p 3000:3000 \
  --env-file .env \
  khang080704/product-service:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up product-service
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
      port: ${MONGODB_PORT:27017}
      database: ${MONGODB_DATABASE:product_service}
      username: ${MONGODB_USERNAME:admin}
      password: ${MONGODB_PASSWORD:password}
  profiles:
    active: dev

logging:
  level:
    org.springframework.data.mongodb: DEBUG
    com.example.productservice: DEBUG

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
  profiles:
    active: prod

logging:
  level:
    com.example.productservice: INFO
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATA_MONGODB_HOST` | MongoDB server host | `localhost` |
| `SPRING_DATA_MONGODB_PORT` | MongoDB server port | `27017` |
| `SPRING_DATA_MONGODB_DATABASE` | MongoDB database name | `product_service` |
| `SPRING_DATA_MONGODB_USERNAME` | MongoDB username | `admin` |
| `SPRING_DATA_MONGODB_PASSWORD` | MongoDB password | `password` |
| `DISCOVERY_SERVER` | Eureka server URL | `http://localhost:8761/eureka/` |

## 📚 API Endpoints

### Product Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/products` | Get all products with pagination | ❌ |
| `GET` | `/api/products/{id}` | Get product by ID | ❌ |
| `POST` | `/api/products` | Create new product | ✅ (Admin) |
| `PUT` | `/api/products/{id}` | Update product | ✅ (Admin) |
| `DELETE` | `/api/products/{id}` | Delete product | ✅ (Admin) |
| `GET` | `/api/products/search` | Search products | ❌ |

### Category Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/categories` | Get all categories | ❌ |
| `GET` | `/api/categories/{id}` | Get category by ID | ❌ |
| `POST` | `/api/categories` | Create new category | ✅ (Admin) |
| `PUT` | `/api/categories/{id}` | Update category | ✅ (Admin) |
| `DELETE` | `/api/categories/{id}` | Delete category | ✅ (Admin) |

### Request/Response Examples

#### Create Product
```bash
POST /api/products
Content-Type: application/json
Authorization: Bearer {jwt-token}

{
  "name": "Wireless Bluetooth Headphones",
  "description": "High-quality wireless headphones with noise cancellation",
  "price": 199.99,
  "categoryId": "64f1a2b3c4d5e6f7g8h9i0j1",
  "brand": "AudioTech",
  "sku": "ATH-WBH-001",
  "inventory": 50,
  "images": [
    "https://example.com/images/headphones-1.jpg",
    "https://example.com/images/headphones-2.jpg"
  ],
  "attributes": {
    "color": "Black",
    "batteryLife": "30 hours",
    "weight": "250g"
  },
  "tags": ["wireless", "bluetooth", "noise-cancelling"]
}
```

#### Search Products
```bash
GET /api/products/search?q=wireless&category=headphones&minPrice=100&maxPrice=300&sort=price&order=asc
```

Response:
```json
{
  "products": [
    {
      "id": "64f1a2b3c4d5e6f7g8h9i0j1",
      "name": "Wireless Bluetooth Headphones",
      "price": 199.99,
      "category": "Electronics",
      "brand": "AudioTech",
      "rating": 4.5,
      "imageUrl": "https://example.com/images/headphones-1.jpg"
    }
  ],
  "total": 1,
  "page": 0,
  "size": 20
}
```

## 📊 Database Schema

### Products Collection
```javascript
{
  _id: ObjectId("64f1a2b3c4d5e6f7g8h9i0j1"),
  name: "Wireless Bluetooth Headphones",
  description: "High-quality wireless headphones with noise cancellation",
  price: 199.99,
  categoryId: ObjectId("64f1a2b3c4d5e6f7g8h9i0j2"),
  brand: "AudioTech",
  sku: "ATH-WBH-001",
  inventory: 50,
  images: ["https://example.com/images/headphones-1.jpg"],
  attributes: {
    color: "Black",
    batteryLife: "30 hours",
    weight: "250g"
  },
  tags: ["wireless", "bluetooth", "noise-cancelling"],
  rating: 4.5,
  reviewCount: 128,
  isActive: true,
  createdAt: ISODate("2025-12-13T10:00:00Z"),
  updatedAt: ISODate("2025-12-13T10:00:00Z")
}
```

### Categories Collection
```javascript
{
  _id: ObjectId("64f1a2b3c4d5e6f7g8h9i0j2"),
  name: "Electronics",
  description: "Electronic devices and accessories",
  parentId: null,
  slug: "electronics",
  imageUrl: "https://example.com/categories/electronics.jpg",
  isActive: true,
  sortOrder: 1,
  createdAt: ISODate("2025-12-13T10:00:00Z"),
  updatedAt: ISODate("2025-12-13T10:00:00Z")
}
```

## 🔍 Search Functionality

### Search Parameters

- **q**: Search query (product name, description, brand)
- **category**: Category filter
- **brand**: Brand filter
- **minPrice/maxPrice**: Price range filter
- **rating**: Minimum rating filter
- **tags**: Tag-based filtering
- **sort**: Sort field (price, rating, name, createdAt)
- **order**: Sort order (asc, desc)

### Advanced Search Features

- **Full-text search** across product names and descriptions
- **Fuzzy matching** for typo tolerance
- **Relevance scoring** based on search terms
- **Facet filtering** for categories, brands, and price ranges
- **Pagination** with customizable page size

## 🔒 Security

### Authentication & Authorization

- **JWT Token Validation**: Integration with User Service
- **Role-based Access**: Admin privileges for product management
- **API Rate Limiting**: Prevent abuse and ensure fair usage
- **Input Validation**: Comprehensive validation for all inputs

### Data Protection

- **Secure Connections**: Encrypted communication with MongoDB
- **Input Sanitization**: Prevention of injection attacks
- **Audit Logging**: Track all product modifications

## 📊 Monitoring

### Health Endpoints

- **Health Check**: http://localhost:3000/actuator/health
- **Info**: http://localhost:3000/actuator/info
- **Metrics**: http://localhost:3000/actuator/metrics
- **MongoDB Health**: Database connection status

### Key Metrics

- Product search query performance
- Database query execution times
- Cache hit/miss ratios
- API response times
- Error rates by endpoint

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

- **Product CRUD Tests**: Create, read, update, delete operations
- **Search Tests**: Various search scenarios and edge cases
- **Category Tests**: Category hierarchy and management
- **Security Tests**: Authentication and authorization
- **Performance Tests**: Search and database query performance

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

1. **MongoDB Connection Failed**
   - Verify MongoDB is running
   - Check connection credentials
   - Ensure database permissions

2. **Search Not Working**
   - Check MongoDB text indexes
   - Verify search query syntax
   - Review search configuration

3. **Image Upload Issues**
   - Check file size limits
   - Verify supported formats
   - Ensure storage permissions

### Debug Mode

Enable debug logging:
```yaml
logging:
  level:
    org.springframework.data.mongodb: DEBUG
    com.example.productservice: DEBUG
```

## 🚀 Performance Optimization

### Database Optimization

- **Indexes**: Optimized indexes on search fields
- **Text Indexes**: Full-text search indexes for product content
- **Compound Indexes**: Multi-field indexes for common queries
- **Aggregation Pipelines**: Efficient data processing

### Caching Strategy

- **Product Cache**: Frequently accessed products
- **Category Cache**: Category hierarchy caching
- **Search Results**: Search query result caching

### Query Optimization

- **Pagination**: Efficient pagination for large result sets
- **Projection**: Return only required fields
- **Aggregation**: Use MongoDB aggregation for complex queries

## 🤝 Contributing

When modifying the Product Service:
1. Maintain backward compatibility for APIs
2. Update database schema carefully
3. Add comprehensive tests for search features
4. Document performance implications
5. Follow MongoDB best practices

## 📝 Future Enhancements

- [ ] Advanced search with AI-powered recommendations
- [ ] Product comparison functionality
- [ ] Bulk product import/export
- [ ] Product review and rating system
- [ ] Advanced filtering and faceted search
- [ ] Product image optimization and CDN integration
- [ ] Multi-language product descriptions
- [ ] Product analytics and reporting

---

**Service**: Product Service
**Port**: 3000
**Database**: MongoDB
**Type**: Business Service
**Last Updated**: December 13, 2025