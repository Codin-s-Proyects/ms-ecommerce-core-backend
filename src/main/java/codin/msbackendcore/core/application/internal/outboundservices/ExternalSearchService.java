package codin.msbackendcore.core.application.internal.outboundservices;

import codin.msbackendcore.search.interfaces.acl.SearchContextFacade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service("CoreExternalSearchService")
public class ExternalSearchService {
    private final SearchContextFacade searchContextFacade;

    public ExternalSearchService(SearchContextFacade searchContextFacade) {
        this.searchContextFacade = searchContextFacade;
    }

    public CompletableFuture<Void> registerMainMediaAssetEmbedding(UUID tenantId, UUID variantId, String aiContext, String productName, String categoryName, String brandName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes) {
        return searchContextFacade.createEmbeddingForImage(tenantId, variantId, aiContext, productName, categoryName, brandName, productDescription, variantName, variantAttributes);
    }
}
