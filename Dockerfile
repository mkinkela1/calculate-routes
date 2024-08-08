FROM maven:3.9.8-eclipse-temurin-22-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/routing.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]