package codin.msbackendcore.pricing.interfaces.acl;

import codin.msbackendcore.pricing.domain.services.PricingDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PricingContextFacade {

    private final PricingDomainService pricingDomainService;

    public PricingContextFacade(PricingDomainService pricingDomainService) {
        this.pricingDomainService = pricingDomainService;
    }

    public void registerDefaultPrices(UUID tenantId, UUID variantId, BigDecimal retailPrice, BigDecimal wholesalePrice) {
        pricingDomainService.createDefaultPrices(tenantId, variantId, retailPrice, wholesalePrice);
    }
}
