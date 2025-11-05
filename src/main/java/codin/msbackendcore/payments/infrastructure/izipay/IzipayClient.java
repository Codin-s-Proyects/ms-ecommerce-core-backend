package codin.msbackendcore.payments.infrastructure.izipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class IzipayClient {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String siteId;
    private final String mode;

    public IzipayClient(
            WebClient izipayWebClient,
            @Value("${izipay.client-id}") String clientId,
            @Value("${izipay.client-secret}") String clientSecret,
            @Value("${izipay.site-id}") String siteId,
            @Value("${izipay.mode:TEST}") String mode
    ) {
        this.webClient = izipayWebClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.siteId = siteId;
        this.mode = mode;
    }

    /**
     * 🔐 Genera un token de seguridad (Security Token)
     */
    public String generateToken() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("clientId", clientId);
        payload.put("clientSecret", clientSecret);

        Map<String, Object> response = webClient.post()
                .uri("/Token/Generate")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(10))
                .block();

        if (response == null || response.get("answer") == null) {
            throw new RuntimeException("Izipay no devolvió un token válido");
        }

        Map<String, Object> answer = (Map<String, Object>) response.get("answer");
        return answer.get("token").toString();
    }

    /**
     * 💳 Confirma una transacción en Izipay
     */
    public Map<String, Object> confirmTransaction(String transactionId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uuidTransaction", transactionId);
        payload.put("siteId", siteId);

        return webClient.post()
                .uri("/api-payment/V4/Transaction/Confirm")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(15))
                .block();
    }

    /**
     * 🧾 Obtiene detalles de una transacción (opcional)
     */
    public Map<String, Object> getTransactionDetails(String transactionId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uuidTransaction", transactionId);
        payload.put("siteId", siteId);

        return webClient.post()
                .uri("/api-payment/V4/Transaction/Get")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(15))
                .block();
    }
}
