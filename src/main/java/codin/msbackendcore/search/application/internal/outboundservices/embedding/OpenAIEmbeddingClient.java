package codin.msbackendcore.search.application.internal.outboundservices.embedding;

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

    /**
     * Llama a la API de embeddings y retorna un float[] con el embedding.
     * Lanzará RuntimeException en caso de error (puedes mapear a excepción de dominio si prefieres).
     */
    public float[] embed(String text) {
        var payload = Map.of(
                "model", model,
                "input", text
        );

        // Aquí usamos block() para simplicidad (sincronía). En producción puedes hacerlo asincrónico.
        Map<String, Object> response = webClient.post()
                .uri("/embeddings")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(20));

        if (response == null || !response.containsKey("data")) {
            throw new RuntimeException("Empty response from OpenAI embeddings");
        }

        List<?> data = (List<?>) response.get("data");
        if (data.isEmpty()) throw new RuntimeException("No embedding data returned");

        Map<?, ?> first = (Map<?, ?>) data.get(0);
        List<Number> embeddingList = (List<Number>) first.get("embedding");

        float[] vector = new float[embeddingList.size()];
        for (int i = 0; i < embeddingList.size(); i++) {
            vector[i] = embeddingList.get(i).floatValue();
        }
        return vector;
    }
}
