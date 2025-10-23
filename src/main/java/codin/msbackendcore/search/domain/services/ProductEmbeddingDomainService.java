package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductEmbeddingDomainService {
    void generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String productDescription,
                                  String variantName, Map<String, Object> variantAttributes);

    List<ProductEmbedding> semanticSearch(UUID tenantId, String query, int limit);
}
