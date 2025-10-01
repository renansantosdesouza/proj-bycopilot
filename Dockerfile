# Build stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="copilot-team"
LABEL application="customer-api"

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Configure working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/v1/actuator/health || exit 1

# Set JVM options for containers
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

# Run with security flags
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -jar app.jar"]