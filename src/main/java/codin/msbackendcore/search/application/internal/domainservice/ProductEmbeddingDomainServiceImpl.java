package codin.msbackendcore.search.application.internal.domainservice;

import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.infrastructure.persistence.jpa.ProductEmbeddingRepository;
import codin.msbackendcore.search.infrastructure.persistence.jpa.ProductEmbeddingRepositoryJdbc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductEmbeddingDomainServiceImpl implements ProductEmbeddingDomainService {

    private final OpenAIEmbeddingClient openAI;
    private final ProductEmbeddingRepositoryJdbc repo;
    private final ProductEmbeddingRepository productEmbeddingRepository;

    public ProductEmbeddingDomainServiceImpl(OpenAIEmbeddingClient openAI, ProductEmbeddingRepositoryJdbc repo, ProductEmbeddingRepository productEmbeddingRepository) {
        this.openAI = openAI;
        this.repo = repo;
        this.productEmbeddingRepository = productEmbeddingRepository;
    }

    public static String toVectorString(float[] embedding) {
        if (embedding == null || embedding.length == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < embedding.length; i++) {
            sb.append(embedding[i]);
            if (i < embedding.length - 1) sb.append(',');
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public CompletableFuture<Void> generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes) {
        String text = buildText(productName, productDescription, variantName, variantAttributes);
        return openAI.embedAsync(text)
                .thenAccept(vector -> {
                    Map<String, Object> metadata = Map.of("name", variantName.replace("\"", "'"));
                    repo.upsertEmbedding(tenantId, variantId, vector, metadata);
                });
    }

    @Override
    public CompletableFuture<List<ProductEmbedding>> semanticSearch(UUID tenantId, String query, int limit) {
        return openAI.embedAsync(query)
                .thenApply(queryEmbedding -> productEmbeddingRepository.findNearestEmbeddings(tenantId, queryEmbedding, limit));
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
