# SDK for Java

[![Build](https://github.com/sarmkadan/anthropic-sdk-java/actions/workflows/build.yml/badge.svg)]()

The SDK for Java provides access to the API from Java applications.

## Installation

### Gradle

```kotlin
implementation("com.anthropic:anthropic-java:2.35.0")
```

### Maven

```xml
<dependency>
  <groupId>com.anthropic</groupId>
  <artifactId>anthropic-java</artifactId>
  <version>2.35.0</version>
</dependency>
```

## Getting started

```java
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;

// Configures using the API_KEY environment variable
AnthropicClient client = AnthropicOkHttpClient.fromEnv();

MessageCreateParams params = MessageCreateParams.builder()
    .maxTokens(1024L)
    .addUserMessage("Hello")
    .build();
Message message = client.messages().create(params);
```

## Usage Examples

See the [examples](examples) directory for complete, runnable examples:

- **[BasicUsage.java](examples/BasicUsage.java)** - Minimal setup and first API call
- **[AdvancedUsage.java](examples/AdvancedUsage.java)** - Custom configuration, streaming, and error handling  
- **[StreamingExample.java](examples/StreamingExample.java)** - Streaming responses for long content
- **[IntegrationExample.java](examples/IntegrationExample.java)** - Spring dependency injection integration

## Docker Support

You can run the Anthropic SDK Java examples in a Docker container. This is useful for testing without installing Java locally.

### Prerequisites

- Docker
- Docker Compose (optional, for multi-container setups)
- Anthropic API key (get one from [Anthropic Console](https://console.anthropic.com/))

> **Note:** The Docker image builds using Gradle. If you encounter build issues, ensure you have a working Gradle installation or build the JAR locally first with `./gradlew :anthropic-java-example:jar`.

### Quick Start

1. **Build the Docker image:**

```bash
# Set your API key as an environment variable
# On Linux/macOS:
export ANTHROPIC_API_KEY="your-api-key-here"

# On Windows (PowerShell):
# $env:ANTHROPIC_API_KEY="your-api-key-here"

# Build the image
docker compose build
```

2. **Run the example:**

```bash
# Run the basic usage example
docker compose run --rm anthropic-sdk-java

# Or start the service in the background
docker compose up -d anthropic-sdk-java

# View logs
docker compose logs -f anthropic-sdk-java
```

3. **Run specific examples:**

```bash
# Run AdvancedUsage example
docker compose run --rm anthropic-sdk-java -Pexample=AdvancedUsage

# Run StreamingExample
# docker compose run --rm anthropic-sdk-java -Pexample=StreamingExample

# Run MessagesExample
# docker compose run --rm anthropic-sdk-java -Pexample=MessagesExample
```

### Using with docker-compose.yml

The provided `docker-compose.yml` includes:

- **anthropic-sdk-java**: Main service running the SDK examples
- **redis**: Optional Redis service for caching (if your application uses Redis)

You can customize the environment and ports in the `docker-compose.yml` file.

### Building a Custom Image

To create a Docker image with your own application:

```dockerfile
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY build/libs/your-application.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
```

### Environment Variables


| Variable | Description | Required |
|----------|-------------|----------|
| `ANTHROPIC_API_KEY` | Your Anthropic API key | Yes |
| `JAVA_TOOL_OPTIONS` | JVM options | Optional |


### Health Check

The Docker image includes a health check that verifies Java is installed and executable.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
