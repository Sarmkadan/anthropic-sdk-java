import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.MessageStream;
import com.anthropic.models.messages.MessageStreamEvent;
import com.anthropic.models.messages.Model;

/**
 * Example demonstrating streaming responses from the Anthropic API.
 */
public class StreamingExample {
    public static void main(String[] args) {
        try (AnthropicClient client = AnthropicOkHttpClient.fromEnv()) {
            MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_20250514)
                .maxTokens(1024L)
                .addUserMessage("Write a long, detailed explanation of how neural networks work.")
                .build();

            System.out.println("Streaming response (watch as text appears):\n");
            
            try (MessageStream stream = client.messages().stream(params)) {
                for (MessageStreamEvent event : stream) {
                    if (event.type() == MessageStreamEvent.Type.CONTENT_BLOCK_DELTA) {
                        var delta = event.contentBlockDelta();
                        if (delta.type().equals("text_delta")) {
                            System.out.print(delta.text());
                        }
                    }
                }
            }
            System.out.println("\n\nStreaming finished.");
            
        } catch (Exception e) {
            System.err.println("Error during streaming: " + e.getMessage());
        }
    }
}
