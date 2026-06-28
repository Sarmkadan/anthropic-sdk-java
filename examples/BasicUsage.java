import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

/**
 * Basic usage example demonstrating a simple message creation.
 */
public class BasicUsage {
    public static void main(String[] args) {
        // Create client using environment variables
        // Ensure ANTHROPIC_API_KEY is set in your environment
        try (AnthropicClient client = AnthropicOkHttpClient.fromEnv()) {
            MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_20250514)
                .maxTokens(1024L)
                .addUserMessage("Hello! Can you explain what Anthropic's Claude AI is in simple terms?")
                .build();

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
            System.err.println("Please set ANTHROPIC_API_KEY environment variable.");
        } catch (Exception e) {
            System.err.println("Error calling Anthropic API: " + e.getMessage());
        }
    }
}
