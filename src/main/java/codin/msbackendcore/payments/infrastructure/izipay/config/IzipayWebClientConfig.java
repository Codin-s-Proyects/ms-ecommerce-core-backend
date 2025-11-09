package codin.msbackendcore.payments.infrastructure.izipay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IzipayWebClientConfig {

    @Value("${izipay.api-url}")
    private String izipayApiUrl;

    @Bean
    public WebClient izipayWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(izipayApiUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}