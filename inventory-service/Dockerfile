FROM eclipse-temurin:17-jre-alpine AS builder
WORKDIR /app

COPY target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:TieredStopAtLevel=1"
ENTRYPOINT ["java", "-jar", "app.jar"]