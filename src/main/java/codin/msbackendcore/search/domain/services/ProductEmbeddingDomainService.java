package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.domain.model.valueobjects.SemanticSearchMode;
import codin.msbackendcore.search.infrastructure.persistence.dto.ProductEmbeddingView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProductEmbeddingDomainService {
    CompletableFuture<Void> generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                     String variantName, Map<String, Object> variantAttributes, Optional<String> aiContextOptional, boolean isCreated);

    CompletableFuture<Void> generateAndSaveEmbeddingWithImageCase(
            UUID tenantId, UUID variantId, String aiContext, String productName, String categoryName, String brandName, String productDescription,
            String variantName, Map<String, Object> variantAttributes
    );

    CompletableFuture<List<ProductEmbeddingView>> semanticSearch(UUID tenantId, String query, int limit, SemanticSearchMode mode, Double distanceThreshold);
    List<String> findSemanticDetails(UUID tenantId, UUID[] variantIds);
}
