package codin.msbackendcore.search.application.internal.outboundservices.embedding;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    @Retry(name = "openaiRetry")
    @CircuitBreaker(name = "openaiCB", fallbackMethod = "fallbackEmbedding")
    @TimeLimiter(name = "openaiTimeout")
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

    private float[] normalize(float[] v) {
        double norm = 0;
        for (float x : v) norm += x * x;
        norm = Math.sqrt(norm);

        float[] out = new float[v.length];
        for (int i = 0; i < v.length; i++) out[i] = (float) (v[i] / norm);
        return out;
    }

    private CompletableFuture<float[]> fallbackEmbedding(String text, Throwable ex) {
        return CompletableFuture.failedFuture(
                new ServerErrorException("error.openai_unavailable", new String[]{})
        );
    }
}
