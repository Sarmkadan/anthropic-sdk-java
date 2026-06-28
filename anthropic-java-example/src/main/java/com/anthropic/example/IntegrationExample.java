import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Integration example showing how to wire the Anthropic client into a Spring application.
 *
 * This example demonstrates:
 * - Creating a Spring configuration class for the Anthropic client
 * - Using the client in a Spring-managed bean
 */
@Configuration
public class IntegrationExample {

    private final AnthropicClient client;

    public IntegrationExample(AnthropicClient client) {
        this.client = client;
    }

    @Bean
    public AnthropicClient anthropicClient() {
        return AnthropicOkHttpClient.fromEnv();
    }

    @PostConstruct
    public void init() {
        // Create message parameters
        MessageCreateParams params = MessageCreateParams.builder()
            .model(Model.CLAUDE_SONNET_4_20250514)
            .maxTokens(1024)
            .addUserMessage("Explain dependency injection in Spring.")
            .build();

        try {
            // Send request and process response
            Message message = client.messages().create(params);

            System.out.println("Response received!");
            System.out.println("Model: " + message.model());
            System.out.println("Total tokens used: " + message.usage().totalTokens());
            System.out.println("\nResponse content:\n");

            message.content().stream()
                .flatMap(contentBlock -> contentBlock.text().stream())
                .forEach(textBlock -> System.out.println(textBlock.text()));
        } catch (Exception e) {
            System.err.println("Error calling Anthropic API: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IntegrationExample.class);
        context.close();
    }
}