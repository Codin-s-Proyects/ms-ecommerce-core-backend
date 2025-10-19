package codin.msbackendcore.catalog.application.internal.outboundservices;

import codin.msbackendcore.pricing.interfaces.acl.PricingContextFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ExternalPricingService {
    private final PricingContextFacade  pricingContextFacade;

    public ExternalPricingService(PricingContextFacade pricingContextFacade) {
        this.pricingContextFacade = pricingContextFacade;
    }

    public void registerVariantPrices(UUID tenantId, UUID variantId, BigDecimal retailPrice, BigDecimal wholesalePrice) {
        pricingContextFacade.registerDefaultPrices(tenantId, variantId, retailPrice, wholesalePrice);
    }
}
