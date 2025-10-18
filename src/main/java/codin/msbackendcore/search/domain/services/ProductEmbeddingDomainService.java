package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.UUID;

public interface ProductEmbeddingDomainService {
    void generateAndSaveEmbedding(UUID tenantId, ProductVariant variant);
    float[] embedText(String text); // if needed externally
}
