package codin.msbackendcore.payments.infrastructure.izipay;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateAmountFormat;

@Component
public class IzipayClient {

    private final WebClient webClient;
    private final String apiKey;
    private final String merchantCode;

    public IzipayClient(
            WebClient izipayWebClient,
            @Value("${izipay.api-key}") String apiKey,
            @Value("${izipay.merchant-code}") String merchantCode
    ) {
        this.webClient = izipayWebClient;
        this.apiKey = apiKey;
        this.merchantCode = merchantCode;
    }


    public String generateToken(String transactionId, BigDecimal amount, String orderNumber) {
        Map<String, String> headers = new HashMap<>();
        headers.put("transactionId", transactionId);
        headers.put("Content-Type", "application/json");

        Map<String, Object> payload = new HashMap<>();
        payload.put("requestSource", "ECOMMERCE");
        payload.put("orderNumber", orderNumber);
        payload.put("publicKey", apiKey);
        payload.put("merchantCode", merchantCode);
        payload.put("amount", generateAmountFormat(amount));



        Map<String, Object> response = webClient.post()
                .uri("/security/v1/Token/Generate")
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(10))
                .block();

        if (response == null || !"00".equals(response.get("code")) || response.get("response") == null)
            throw new ServerErrorException("error.server_error", new String[]{});


        Map<String, Object> answer = (Map<String, Object>) response.get("response");
        if (answer.get("token") == null)
            throw new ServerErrorException("error.server_error", new String[]{});

        return answer.get("token").toString();
    }

    /**
     * 💳 Confirma una transacción en Izipay
     */
    public Map<String, Object> confirmTransaction(String transactionId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uuidTransaction", transactionId);
        payload.put("siteId", "fafadfa");

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
        payload.put("siteId", "siteId");

        return webClient.post()
                .uri("/api-payment/V4/Transaction/Get")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(15))
                .block();
    }
}
