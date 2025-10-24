package codin.msbackendcore.search.application.internal.queryservice;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.domain.services.ProductEmbeddingQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductEmbeddingQueryServiceImpl implements ProductEmbeddingQueryService {

    private final ProductEmbeddingDomainService productEmbeddingDomainService;

    public ProductEmbeddingQueryServiceImpl(ProductEmbeddingDomainService productEmbeddingDomainService) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
    }

    @Override
    public CompletableFuture<List<ProductEmbedding>> handle(SemanticSearchQuery query) {
        return productEmbeddingDomainService.semanticSearch(
                query.tenantId(),
                query.query(),
                query.limit()
        );
    }
}
