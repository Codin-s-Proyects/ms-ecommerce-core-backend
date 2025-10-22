package codin.msbackendcore.search.interfaces.acl;

import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class SearchContextFacade {
    private final ProductEmbeddingDomainService productEmbeddingDomainService;

    public SearchContextFacade(ProductEmbeddingDomainService productEmbeddingDomainService) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
    }

    public void createEmbeddingForVariant(UUID tenantId, UUID variantId, String productName, String productDescription,
                                          String variantName, Map<String, Object> variantAttributes) {
        productEmbeddingDomainService.generateAndSaveEmbedding(tenantId, variantId, productName, productDescription, variantName, variantAttributes);
    }
}