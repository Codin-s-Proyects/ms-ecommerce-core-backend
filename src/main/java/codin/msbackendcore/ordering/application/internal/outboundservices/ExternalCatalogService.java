package codin.msbackendcore.ordering.application.internal.outboundservices;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import codin.msbackendcore.core.interfaces.acl.CoreContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCatalogServiceForOrdering")
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade) {
        this.catalogContextFacade = catalogContextFacade;
    }

    public boolean existsProductVariantById(UUID productVariantId) {
        return catalogContextFacade.getProductVariantById(productVariantId) != null;
    }
}
