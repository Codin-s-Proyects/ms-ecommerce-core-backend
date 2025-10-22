package codin.msbackendcore.search.application.internal.domainservice;

import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.infrastructure.persistence.jpa.ProductEmbeddingRepositoryJdbc;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ProductEmbeddingDomainServiceImpl implements ProductEmbeddingDomainService {

    private final OpenAIEmbeddingClient openAI;
    private final ProductEmbeddingRepositoryJdbc repo;

    public ProductEmbeddingDomainServiceImpl(OpenAIEmbeddingClient openAI, ProductEmbeddingRepositoryJdbc repo) {
        this.openAI = openAI;
        this.repo = repo;
    }

    @Override
    public void generateAndSaveEmbedding(UUID tenantId, UUID variantId , String productName, String productDescription,
                                         String variantName, Map<String, Object> variantAttributes) {
        String text = buildText(productName, productDescription, variantName, variantAttributes);
        float[] vector = openAI.embed(text);
        Map<String, Object> metadata = Map.of("name", variantName.replace("\"", "'"));
        repo.upsertEmbedding(tenantId, variantId, vector, metadata);
    }

    private String buildText(String productName, String productDescription,
                             String variantName, Map<String, Object> variantAttributes) {

        StringBuilder textBuilder = new StringBuilder();

        if (productName != null) {
            textBuilder.append(productName).append(" ");
        }

        if (productDescription != null) {
            textBuilder.append(productDescription).append(" ");
        }

        if (variantName != null) {
            textBuilder.append(variantName).append(" ");
        }

        if (variantAttributes != null) {
            textBuilder.append(variantAttributes);
        }

        return textBuilder.toString().trim();
    }
}
