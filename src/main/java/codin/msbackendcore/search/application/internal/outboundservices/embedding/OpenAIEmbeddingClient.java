package codin.msbackendcore.search.application.internal.outboundservices.embedding;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class OpenAIEmbeddingClient {

    private final WebClient webClient;
    private final String model;

    public OpenAIEmbeddingClient(WebClient openAiWebClient,
                                 @Value("${openai.model:text-embedding-3-small}") String model,
                                 @Value("${openai.timeout-ms:10000}") int timeoutMs) {
        this.webClient = openAiWebClient;
        this.model = model;
    }

    public float[] embed(String text) {
        var payload = Map.of(
                "model", model,
                "input", text
        );

        Map<String, Object> response = webClient.post()
                .uri("/embeddings")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(5));

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
