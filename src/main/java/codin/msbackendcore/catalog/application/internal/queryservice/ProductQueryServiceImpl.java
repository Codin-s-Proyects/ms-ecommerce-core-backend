package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.ProductQueryService;
import codin.msbackendcore.catalog.domain.services.ProductSearchDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductSearchDomainService searchDomainService;

    public ProductQueryServiceImpl(ProductSearchDomainService searchDomainService) {
        this.searchDomainService = searchDomainService;
    }

    public List<ProductVariant> handleSearch(UUID tenantId, String query, int limit) {
        return searchDomainService.searchSimilarProducts(tenantId, query, limit);
    }
}
