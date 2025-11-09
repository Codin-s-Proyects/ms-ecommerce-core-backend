package codin.msbackendcore.search.interfaces.acl;

import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchContextFacade {
    private final ProductEmbeddingDomainService productEmbeddingDomainService;

    public SearchContextFacade(ProductEmbeddingDomainService productEmbeddingDomainService) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
    }

    public CompletableFuture<Void> createEmbeddingForVariant(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                             String variantName, Map<String, Object> variantAttributes) {
        return productEmbeddingDomainService.generateAndSaveEmbedding(tenantId, variantId, productName, categoryName, brandName, productDescription, variantName, variantAttributes);
    }
}