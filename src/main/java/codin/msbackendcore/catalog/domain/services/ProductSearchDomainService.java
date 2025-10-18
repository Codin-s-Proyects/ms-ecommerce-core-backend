package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.List;
import java.util.UUID;

public interface ProductSearchDomainService {
    List<ProductVariant> searchSimilarProducts(UUID tenantId, String query, int limit);
}