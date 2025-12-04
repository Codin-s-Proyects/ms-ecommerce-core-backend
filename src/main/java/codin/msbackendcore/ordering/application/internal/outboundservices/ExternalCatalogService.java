package codin.msbackendcore.ordering.application.internal.outboundservices;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void reserve(UUID variantId, UUID tenantId, int qty) {

        catalogContextFacade.reserve(variantId, tenantId, qty);
    }

    @Transactional
    public void release(UUID variantId, UUID tenantId, int qty) {
        catalogContextFacade.release(variantId, tenantId, qty);
    }

    @Transactional
    public void confirm(UUID variantId, UUID tenantId, int qty) {
        catalogContextFacade.confirm(variantId, tenantId, qty);
    }
}
