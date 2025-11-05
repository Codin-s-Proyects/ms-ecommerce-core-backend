package codin.msbackendcore.ordering.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class IzipayClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${izipay.api.base-url}")
    private String baseUrl;

    @Value("${izipay.api.key}")
    private String apiKey;

    public Map<String, Object> verifyTransaction(String transactionId) {
        String url = baseUrl + "/v1/transactions/" + transactionId;

        return restTemplate.getForObject(url + "?apikey=" + apiKey, Map.class);
    }
}
