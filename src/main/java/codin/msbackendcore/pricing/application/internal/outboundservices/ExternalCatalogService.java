package codin.msbackendcore.pricing.application.internal.outboundservices;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ExternalCatalogServiceForPricing")
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade) {
        this.catalogContextFacade = catalogContextFacade;
    }

    public boolean existsProductVariantById(UUID productVariantId) {
        return catalogContextFacade.getProductVariantById(productVariantId) != null;
    }
}
