package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.search.interfaces.acl.SearchContextFacade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("CatalogExternalSearchService")
public class ExternalSearchService {
    private final SearchContextFacade searchContextFacade;

    public ExternalSearchService(SearchContextFacade searchContextFacade) {
        this.searchContextFacade = searchContextFacade;
    }

    public CompletableFuture<Void> saveProductEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes, Optional<String> aiContext) {
        return searchContextFacade.createEmbeddingForVariant(tenantId, variantId, productName, categoryName, brandName, productDescription, variantName, variantAttributes, aiContext);
    }

    public CompletableFuture<Void> updateProductEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                        String variantName, Map<String, Object> variantAttributes, Optional<String> aiContext) {
        return searchContextFacade.updateEmbeddingForVariant(tenantId, variantId, productName, categoryName, brandName, productDescription, variantName, variantAttributes, aiContext);
    }
}
