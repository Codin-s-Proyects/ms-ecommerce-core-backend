package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.List;
import java.util.UUID;

public interface ProductQueryService {
    List<ProductVariant> handleSearch(UUID tenantId, String query, int limit);
}
