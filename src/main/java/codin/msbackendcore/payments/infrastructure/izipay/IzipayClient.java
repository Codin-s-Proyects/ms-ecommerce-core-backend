package codin.msbackendcore.payments.infrastructure.izipay;

import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class IzipayClient {

    private final WebClient webClient;
    private final String merchantCode;
    private final String publicKey;
    private final String password;

    public IzipayClient(
            WebClient izipayWebClient,
            @Value("${izipay.public-key}") String publicKey,
            @Value("${izipay.merchant-code}") String merchantCode,
            @Value("${izipay.password}") String password
    ) {
        this.webClient = izipayWebClient;
        this.publicKey = publicKey;
        this.merchantCode = merchantCode;
        this.password = password;
    }

    @Retry(name = "izipayRetry")
    @CircuitBreaker(name = "izipayCB", fallbackMethod = "fallbackPayment")
    public String generateToken(String transactionId, BigDecimal amount, String orderNumber) {
        String encoded = Base64.getEncoder().encodeToString((merchantCode + ":" + password).getBytes(StandardCharsets.UTF_8));

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", transactionId);
        payload.put("currency", "PEN");
        payload.put("amount", amount);



        Map<String, Object> response = webClient.post()
                .uri("/api-payment/V4/Charge/CreatePayment")
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(10))
                .block();

        if (response == null || !"SUCCESS".equals(response.get("status")) || response.get("answer") == null)
            throw new ServerErrorException("error.server_error", new String[]{});


        Map<String, Object> answer = (Map<String, Object>) response.get("answer");
        if (answer.get("formToken") == null)
            throw new ServerErrorException("error.server_error", new String[]{});

        return answer.get("formToken").toString();
    }

    private String fallbackPayment(String transactionId, BigDecimal amount, String orderNumber, Throwable ex) {
        throw new ServerErrorException("error.izipay_unavailable", new String[]{});
    }
}
