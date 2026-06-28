import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

/**
 * Basic usage example showing minimal setup and first API call.
 *
 * This example demonstrates:
 * - Creating a client using environment variables
 * - Making a simple message request
 * - Processing and printing the response
 * - Error handling for missing API key
 */
public class BasicUsage {

    public static void main(String[] args) {
        // Create client using ANTHROPIC_API_KEY environment variable
        // Set your API key before running: export ANTHROPIC_API_KEY="your-api-key-here"
        AnthropicClient client = AnthropicOkHttpClient.fromEnv();

        // Create message parameters
        MessageCreateParams params = MessageCreateParams.builder()
            .model(Model.CLAUDE_SONNET_4_20250514)
            .maxTokens(1024L)
            .addUserMessage("Hello! Can you explain what Anthropic's Claude AI is in simple terms?")
            .build();

        try {
            // Send request and process response
            Message message = client.messages().create(params);

            System.out.println("Response received successfully!");
            System.out.println("Model used: " + message.model());
            System.out.println("Usage tokens: " + message.usage().totalTokens());
            System.out.println("\n--- Response Content ---");

            message.content().stream()
                .flatMap(contentBlock -> contentBlock.text().stream())
                .forEach(textBlock -> System.out.println(textBlock.text()));

        } catch (IllegalStateException e) {
            System.err.println("Error: API key not configured.");
            System.err.println("Please set ANTHROPIC_API_KEY environment variable or provide it explicitly.");
            System.err.println("Example: export ANTHROPIC_API_KEY='your-api-key-here'");
        } catch (Exception e) {
            System.err.println("Error calling Anthropic API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}