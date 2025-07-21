# --- Stage 1: Build with JDK 24 ---
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /workspace

# Copy Maven wrapper and go‑offline for dependencies
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline -B

# Copy source & build
COPY src src
RUN ./mvnw clean package -DskipTests -B

# --- Stage 2: Run on the same JDK 24 runtime ---
FROM eclipse-temurin:24-jdk
WORKDIR /app

# Copy the packaged jar from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Launch the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
