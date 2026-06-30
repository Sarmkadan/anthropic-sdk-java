# Dockerfile for sarmkadan/anthropic-sdk-java
# This image demonstrates usage of the Anthropic Java SDK
# It includes the SDK and a simple example application

# Build stage: compile the example application
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /workspace

# Install Gradle wrapper dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
  curl \
  unzip \
  ca-certificates \
  && rm -rf /var/lib/apt/lists/*


# Copy gradle wrapper files first to leverage docker cache
COPY gradle/wrapper/gradle-wrapper.jar /workspace/gradle/wrapper/gradle-wrapper.jar
COPY gradle/wrapper/gradle-wrapper.properties /workspace/gradle/wrapper/gradle-wrapper.properties
COPY gradlew /workspace/
COPY gradlew.bat /workspace/
COPY build.gradle.kts /workspace/
COPY settings.gradle.kts /workspace/
COPY gradle.properties /workspace/

# Make gradlew executable
RUN chmod +x /workspace/gradlew

# Copy the rest of the project files
COPY . /workspace

# Build the example application
RUN ./gradlew :anthropic-java-example:jar --no-daemon

# Runtime stage: create a minimal image with just the JAR and Java
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /workspace/anthropic-java-example/build/libs/anthropic-java-example-*.jar /app/app.jar

# Copy examples directory for reference
COPY examples /app/examples

# Set environment variable for the example to run
ENV ANTHROPIC_API_KEY="your-api-key-here"

# Health check - verify Java can execute (simple check that does not require API key)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD java -version

# Default command runs a basic usage example
CMD ["java", "-jar", "/app/app.jar"]

EXPOSE 8080
