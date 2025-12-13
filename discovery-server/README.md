# Discovery Server

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)](https://spring.io/projects/spring-cloud)
[![Eureka](https://img.shields.io/badge/Eureka-Server-orange)](https://github.com/Netflix/eureka)

The Discovery Server is the service registry component of the microservices architecture, built with Netflix Eureka Server. It provides dynamic service registration and discovery capabilities for all microservices in the system.

## 🎯 Overview

The Discovery Server acts as a central registry where all microservices register themselves and discover other services. This enables:

- **Dynamic Service Discovery**: Services can find each other without hardcoded URLs
- **Load Balancing**: Client-side load balancing across service instances
- **Service Health Monitoring**: Automatic detection of service availability
- **Fault Tolerance**: Automatic removal of unhealthy service instances

## 🚀 Features

- **Service Registration**: Automatic registration of microservices
- **Service Discovery**: Dynamic lookup of service instances
- **Health Checks**: Continuous monitoring of service health
- **Dashboard**: Web-based UI for monitoring registered services
- **Self-Preservation**: Protection against network partitions

## 🛠️ Technology Stack

- **Spring Boot 3.5.7**: Framework for building the application
- **Spring Cloud Netflix Eureka Server**: Service discovery server
- **Spring Boot Actuator**: Health monitoring and metrics
- **Docker**: Containerization support

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (for containerized deployment)

## 🚀 Quick Start

### Local Development

1. **Navigate to discovery-server directory**
   ```bash
   cd discovery-server
   ```

2. **Run with Maven**
   ```bash
   mvn spring-boot:run
   ```

3. **Or run the JAR file**
   ```bash
   mvn clean package
   java -jar target/discovery-server-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment

```bash
# Build the Docker image
docker build -t khang080704/discovery-server:latest .

# Run the container
docker run -p 8761:8761 khang080704/discovery-server:latest
```

### Docker Compose (Recommended)

```bash
# From project root
docker-compose up discovery-server
```

## 🔧 Configuration

### Application Properties

The service is configured through `application.yml` with the following key settings:

```yaml
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false  # Server doesn't register itself
    fetchRegistry: false       # Server doesn't fetch registry
  server:
    enableSelfPreservation: true
```

### Environment Variables

- `EUREKA_INSTANCE_HOSTNAME`: Hostname for Eureka instance (default: localhost)
- `SERVER_PORT`: Server port (default: 8761)

## 📊 Monitoring

### Eureka Dashboard

Access the Eureka dashboard at: http://localhost:8761

The dashboard provides:
- **Registered Services**: List of all registered microservices
- **Service Instances**: Individual instances with status
- **Health Status**: UP/DOWN status for each service
- **Metadata**: Additional information about each service

### Health Endpoints

- **Health Check**: http://localhost:8761/actuator/health
- **Info**: http://localhost:8761/actuator/info
- **Metrics**: http://localhost:8761/actuator/metrics

## 🔗 Service Registration

Microservices register with the Discovery Server by:

1. Adding `@EnableEurekaClient` annotation
2. Configuring `eureka.client.serviceUrl.defaultZone` in application.yml
3. Setting appropriate instance metadata

Example configuration for a client service:
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    preferIpAddress: true
```

## 🏗️ Architecture

### Service Registry Flow

1. **Service Startup**: Microservice starts and registers with Eureka
2. **Heartbeat**: Service sends periodic heartbeats to maintain registration
3. **Service Discovery**: Other services query Eureka for service locations
4. **Load Balancing**: Client-side load balancer distributes requests
5. **Health Monitoring**: Eureka monitors service health and removes unhealthy instances

### High Availability

For production deployments, consider:
- **Multiple Eureka Servers**: Peer-to-peer replication
- **Zone Awareness**: Services prefer instances in the same zone
- **Backup Registries**: Fallback registries for resilience

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

1. Start the Discovery Server
2. Access the dashboard at http://localhost:8761
3. Start other microservices and verify they appear in the registry
4. Test service discovery by making inter-service calls

## 🔒 Security Considerations

- **Network Security**: Restrict access to Eureka dashboard in production
- **Service Authentication**: Implement mutual TLS for service communication
- **Access Control**: Configure proper firewall rules
- **Monitoring**: Enable audit logging for service registrations

## 🚧 Troubleshooting

### Common Issues

1. **Services not registering**
   - Check network connectivity
   - Verify Eureka server URL configuration
   - Check service logs for registration errors

2. **Services showing as DOWN**
   - Verify service health check endpoints
   - Check network connectivity
   - Review service configuration

3. **Dashboard not accessible**
   - Confirm server is running on port 8761
   - Check firewall settings
   - Verify no port conflicts

### Debug Mode

Enable debug logging in `application.yml`:
```yaml
logging:
  level:
    com.netflix.eureka: DEBUG
    com.netflix.discovery: DEBUG
```

## 📚 API Reference

The Discovery Server provides REST APIs for:
- Service registration and deregistration
- Service discovery queries
- Health status reporting
- Instance metadata management

## 🤝 Contributing

When modifying the Discovery Server:
1. Maintain backward compatibility
2. Update documentation for configuration changes
3. Test with all dependent services
4. Follow Spring Boot and Eureka best practices

## 📝 Future Enhancements

- [ ] Implement Eureka Server clustering
- [ ] Add service mesh integration
- [ ] Implement advanced health checks
- [ ] Add metrics and monitoring dashboard
- [ ] Support for Kubernetes service discovery

---

**Service**: Discovery Server
**Port**: 8761
**Type**: Infrastructure Service
**Last Updated**: December 13, 2025