package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.search.interfaces.acl.SearchContextFacade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ExternalSearchService {
    private final SearchContextFacade searchContextFacade;

    public ExternalSearchService(SearchContextFacade searchContextFacade) {
        this.searchContextFacade = searchContextFacade;
    }

    public void registerProductEmbedding(UUID tenantId, UUID variantId, String productName, String productDescription,
                                         String variantName, Map<String, Object> variantAttributes) {
        searchContextFacade.createEmbeddingForVariant(tenantId, variantId, productName, productDescription, variantName, variantAttributes);
    }
}
