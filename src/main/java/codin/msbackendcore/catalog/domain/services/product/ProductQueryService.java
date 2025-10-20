package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.infrastructure.persistence.dto.ProductSearchResult;

import java.util.List;
import java.util.UUID;

public interface ProductQueryService {
    List<ProductSearchResult> handleSearch(UUID tenantId, String query, int limit);
}
