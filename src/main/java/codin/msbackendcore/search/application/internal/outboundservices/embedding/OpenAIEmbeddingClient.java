package codin.msbackendcore.search.application.internal.outboundservices.embedding;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OpenAIEmbeddingClient {

    private final WebClient webClient;
    private final String model;
    private final int timeoutMs;
    private final ExecutorService virtualThreadExecutor;

    public OpenAIEmbeddingClient(WebClient openAiWebClient,
                                 @Value("${openai.model:text-embedding-3-small}") String model,
                                 @Value("${openai.timeout-ms:10000}") int timeoutMs) {
        this.webClient = openAiWebClient;
        this.model = model;
        this.timeoutMs = timeoutMs;
        this.virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public CompletableFuture<float[]> embedAsync(String text) {
        return CompletableFuture.supplyAsync(() -> embedBlocking(text), virtualThreadExecutor);
    }

    private float[] embedBlocking(String text) {
        var payload = Map.of(
                "model", model,
                "input", text
        );

        Map<String, Object> response = webClient.post()
                .uri("/embeddings")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || !response.containsKey("data")) {
            throw new ServerErrorException("error.server_error", new String[]{});
        }

        List<?> data = (List<?>) response.get("data");
        if (data.isEmpty()) throw new ServerErrorException("error.server_error", new String[]{});

        Map<?, ?> first = (Map<?, ?>) data.get(0);
        List<Number> embeddingList = (List<Number>) first.get("embedding");

        float[] vector = new float[embeddingList.size()];
        for (int i = 0; i < embeddingList.size(); i++) {
            vector[i] = embeddingList.get(i).floatValue();
        }
        return vector;
    }
}
