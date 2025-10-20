package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.infrastructure.persistence.dto.ProductSearchResult;

import java.util.List;
import java.util.UUID;

public interface ProductSearchDomainService {
    List<ProductSearchResult> searchSimilarProducts(UUID tenantId, String query, int limit);
}