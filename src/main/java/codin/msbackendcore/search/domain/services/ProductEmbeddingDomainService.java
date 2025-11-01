package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProductEmbeddingDomainService {
    CompletableFuture<Void> generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                     String variantName, Map<String, Object> variantAttributes);

    CompletableFuture<List<ProductEmbedding>> semanticSearch(UUID tenantId, String query, int limit);
}
