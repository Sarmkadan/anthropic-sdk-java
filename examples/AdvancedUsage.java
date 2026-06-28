import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.core.ClientOptions;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.MessageStream;
import com.anthropic.models.messages.Model;
import okhttp3.OkHttpClient;
import java.time.Duration;

/**
 * Advanced usage example demonstrating streaming, custom options, and different models.
 */
public class AdvancedUsage {
    public static void main(String[] args) {
        System.out.println("=== Streaming Response Example ===");
        streamMessage("Write a short story about a robot learning to paint.");

        System.out.println("\n=== Structured Request Example ===");
        structuredMessage("Explain quantum computing in simple terms for a high school student.");

        System.out.println("\n=== Conversation with System Message ===");
        conversationWithSystemMessage();

        System.out.println("\n=== Different Models Example ===");
        useDifferentModels();
    }

    private static void streamMessage(String prompt) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .timeout(Duration.ofSeconds(30))
            .maxRetries(3)
            .logLevel(ClientOptions.LogLevel.BASIC)
            .build();

        try (AnthropicClient client = AnthropicOkHttpClient.builder()
            .httpClient(httpClient)
            .options(clientOptions)
            .apiKey(System.getenv("ANTHROPIC_API_KEY"))
            .build()) {

            MessageCreateParams streamingParams = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_20250514)
                .maxTokens(2048)
                .addUserMessage(prompt)
                .build();

            System.out.println("Streaming response (watch as text appears):\n");
            try (MessageStream stream = client.messages().stream(streamingParams)) {
                for (var event : stream) {
                    if (event.type() == com.anthropic.models.messages.MessageStreamEvent.Type.CONTENT_BLOCK_DELTA) {
                        var delta = event.contentBlockDelta();
                        if (delta.type().equals("text_delta")) {
                            System.out.print(delta.text());
                        }
                    }
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            System.err.println("Streaming error: " + e.getMessage());
        }
    }

    private static void structuredMessage(String prompt) {
        try (AnthropicClient client = AnthropicOkHttpClient.fromEnv()) {
            MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_20250514)
                .maxTokens(1024)
                .temperature(0.7f)
                .topP(0.9f)
                .addUserMessage(prompt)
                .build();

            Message message = client.messages().create(params);
            
            System.out.println("Response received!");
            System.out.println("Model: " + message.model());
            System.out.println("Total tokens used: " + message.usage().totalTokens());
            System.out.println("\nResponse content:\n");
            
            message.content().stream()
                .flatMap(contentBlock -> contentBlock.text().stream())
                .forEach(textBlock -> System.out.println(textBlock.text()));
                
        } catch (Exception e) {
            System.err.println("Structured message error: " + e.getMessage());
        }
    }

    private static void conversationWithSystemMessage() {
        try (AnthropicClient client = AnthropicOkHttpClient.fromEnv()) {
            String systemMessage = "You are a helpful assistant that explains concepts in simple terms. " +
                "Use analogies and examples from everyday life.";
            
            MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_20250514)
                .maxTokens(1024)
                .systemMessage(systemMessage)
                .addUserMessage("What is photosynthesis?")
                .build();

            Message message = client.messages().create(params);
            
            System.out.println("Conversation response:");
            message.content().stream()
                .flatMap(contentBlock -> contentBlock.text().stream())
                .forEach(textBlock -> System.out.println(textBlock.text()));
                
        } catch (Exception e) {
            System.err.println("Conversation error: " + e.getMessage());
        }
    }

    private static void useDifferentModels() {
        try (AnthropicClient client = AnthropicOkHttpClient.fromEnv()) {
            Model[] models = { 
                Model.CLAUDE_SONNET_4_20250514, 
                Model.CLAUDE_OPUS_4_8, 
                Model.CLAUDE_HAIKU_4_5_20251001 
            };
            
            String prompt = "Summarize the key points of artificial intelligence in 3 bullet points.";
            
            for (Model model : models) {
                System.out.println("\n--- Testing model: " + model + " ---");
                MessageCreateParams params = MessageCreateParams.builder()
                    .model(model)
                    .maxTokens(512)
                    .addUserMessage(prompt)
                    .build();
                
                Message message = client.messages().create(params);
                System.out.println("Response received from " + model);
                System.out.println("Tokens used: " + message.usage().totalTokens());
            }
        } catch (Exception e) {
            System.err.println("Model comparison error: " + e.getMessage());
        }
    }
}
