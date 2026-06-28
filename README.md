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

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
