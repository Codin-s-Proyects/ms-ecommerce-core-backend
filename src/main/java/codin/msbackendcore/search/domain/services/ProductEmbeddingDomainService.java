package codin.msbackendcore.search.domain.services;

import java.util.Map;
import java.util.UUID;

public interface ProductEmbeddingDomainService {
    void generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String productDescription,
                                  String variantName, Map<String, Object> variantAttributes);
}
