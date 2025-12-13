# Notification Service

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-2.8.0-blue)](https://kafka.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8.1-red)](https://maven.apache.org/)

## 📋 Overview

The Notification Service is a microservice responsible for handling asynchronous notifications in the e-commerce platform. It listens to Kafka events, particularly order-related events, and sends notifications to users via email or other communication channels.

## 🚀 Features

- **Event-Driven Architecture**: Listens to Kafka topics for real-time event processing
- **Order Notifications**: Automatically sends notifications when orders are placed
- **Scalable**: Built with Spring Boot for high performance and scalability
- **Fault Tolerant**: Implements proper error handling and retry mechanisms

## 🏗️ Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Order Service │───▶│   Kafka Topic    │───▶│Notification     │
│                 │    │  (order-topic)   │    │   Service       │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                                       │
                                                       ▼
                                               ┌─────────────────┐
                                               │   Email/SMS     │
                                               │   Provider      │
                                               └─────────────────┘
```

## 📋 Dependencies

- **Spring Boot Starter Web**: For REST API endpoints
- **Spring Kafka**: For Kafka integration and event consumption
- **Lombok**: For reducing boilerplate code
- **Spring Boot Starter Test**: For unit and integration testing

## ⚙️ Configuration

### Application Properties

```properties
spring.application.name=notification-service
```

### Kafka Configuration

The service is configured to consume from the `order-topic` with the consumer group `notification-group`.

**Key Configuration:**
- **Bootstrap Servers**: `localhost:9092`
- **Group ID**: `notification-group`
- **Topic**: `order-topic`
- **Trusted Packages**: `*` (for JSON deserialization)

## 🔧 Installation & Setup

### Prerequisites

- Java 17 or higher
- Apache Kafka running on `localhost:9092`
- Maven 3.8.1 or higher

### Build & Run

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd notification-service
   ```

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

## 📡 API Endpoints

Currently, the service primarily consumes Kafka events rather than exposing REST endpoints. Future enhancements may include REST APIs for manual notification triggers.

## 📨 Event Processing

### Order Placed Event

The service listens for `OrderPlacedEvent` messages on the `order-topic`:

```java
{
  "orderId": 12345,
  "userId": 67890,
  "total": 299.99
}
```

**Processing Flow:**
1. Event received from Kafka
2. Event data extracted and validated
3. Notification sent to user (email implementation pending)
4. Processing logged for monitoring

## 🔍 Monitoring & Logging

- **Spring Boot Actuator**: Provides health checks and metrics endpoints
- **Console Logging**: All events and processing steps are logged to console
- **Error Handling**: Comprehensive error handling with appropriate logging

## 🧪 Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
The service includes Kafka integration tests to verify event consumption and processing.

## 🔐 Security

- **No Authentication Required**: Service consumes internal events only
- **Network Security**: Should be deployed in secure network segments
- **Data Validation**: All incoming events are validated before processing

## 🚀 Deployment

### Docker Deployment

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose

```yaml
version: '3.8'
services:
  notification-service:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - kafka
    environment:
      - SPRING_PROFILES_ACTIVE=docker
```

## 🔗 Dependencies

- **Eureka Server**: For service discovery (inherited from parent POM)
- **API Gateway**: Routes external requests (not directly used)
- **Order Service**: Publishes events consumed by this service
- **Kafka**: Message broker for event streaming

## 📈 Future Enhancements

- [ ] Email service integration (SendGrid, AWS SES)
- [ ] SMS notifications via Twilio
- [ ] Push notifications for mobile apps
- [ ] Notification templates and customization
- [ ] Notification history and tracking
- [ ] REST API for manual notification triggers
- [ ] Multi-channel notification support

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions, please contact the development team or create an issue in the repository.</content>
<parameter name="filePath">f:\Dai hoc\Java\Microservice\notification-service\README.md