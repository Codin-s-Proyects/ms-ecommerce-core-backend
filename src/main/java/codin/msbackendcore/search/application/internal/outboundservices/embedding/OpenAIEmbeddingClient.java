package codin.msbackendcore.search.application.internal.outboundservices.embedding;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OpenAIEmbeddingClient {

    private final WebClient webClient;
    private final String model;

    public OpenAIEmbeddingClient(WebClient openAiWebClient,
                                 @Value("${openai.model:text-embedding-3-small}") String model) {
        this.webClient = openAiWebClient;
        this.model = model;
    }

    @Bulkhead(name = "openaiBulkhead", type = Bulkhead.Type.SEMAPHORE)
    @Retry(name = "openaiRetry")
    @CircuitBreaker(name = "openaiCB", fallbackMethod = "fallbackEmbedding")
    @TimeLimiter(name = "openaiTimeout")
    public CompletableFuture<float[]> embedAsync(String text) {
        var payload = Map.of(
                "model", model,
                "input", text
        );

        return webClient.post()
                .uri("/embeddings")
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.createException()
                                .flatMap(Mono::error)
                )
                .bodyToMono(Map.class)
                .map(this::extractEmbedding)
                .toFuture();
    }

    private float[] extractEmbedding(Map<String, Object> response) {
        List<?> data = (List<?>) response.get("data");
        Map<?, ?> first = (Map<?, ?>) data.get(0);
        List<Number> embedding = (List<Number>) first.get("embedding");

        float[] vector = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            vector[i] = embedding.get(i).floatValue();
        }
        return vector;
    }

    public CompletableFuture<float[]> fallbackEmbedding(String text, Throwable ex) {

        if (ex instanceof io.github.resilience4j.bulkhead.BulkheadFullException) {
            return CompletableFuture.failedFuture(
                    new ServerErrorException("error.openai_overloaded", new String[]{ex.getMessage()})
            );
        }

        if (ex instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            return CompletableFuture.failedFuture(
                    new ServerErrorException("error.openai_circuit_open", new String[]{ex.getMessage()})
            );
        }

        if (ex instanceof java.util.concurrent.TimeoutException) {
            return CompletableFuture.failedFuture(
                    new ServerErrorException("error.openai_timeout", new String[]{ex.getMessage()})
            );
        }

        return CompletableFuture.failedFuture(
                new ServerErrorException("error.openai_unavailable", new String[]{ex.getMessage()})
        );
    }
}
