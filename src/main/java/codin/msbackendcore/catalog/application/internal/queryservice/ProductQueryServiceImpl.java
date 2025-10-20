package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.catalog.domain.services.product.ProductSearchDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.dto.ProductSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductSearchDomainService searchDomainService;

    public ProductQueryServiceImpl(ProductSearchDomainService searchDomainService) {
        this.searchDomainService = searchDomainService;
    }

    public List<ProductSearchResult> handleSearch(UUID tenantId, String query, int limit) {
        return searchDomainService.searchSimilarProducts(tenantId, query, limit);
    }
}
