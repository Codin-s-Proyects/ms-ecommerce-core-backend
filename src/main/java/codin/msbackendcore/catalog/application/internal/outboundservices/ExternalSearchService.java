package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.search.interfaces.acl.SearchContextFacade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalSearchService {
    private final SearchContextFacade searchContextFacade;

    public ExternalSearchService(SearchContextFacade searchContextFacade) {
        this.searchContextFacade = searchContextFacade;
    }

    public CompletableFuture<Void> registerProductEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes) {
        return searchContextFacade.createEmbeddingForVariant(tenantId, variantId, productName, categoryName, brandName, productDescription, variantName, variantAttributes);
    }

    public CompletableFuture<Void> updateProductEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes) {
        return searchContextFacade.updateEmbeddingForVariant(tenantId, variantId, productName, categoryName, brandName, productDescription, variantName, variantAttributes);
    }
}
