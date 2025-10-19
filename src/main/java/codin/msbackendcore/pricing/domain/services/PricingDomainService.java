package codin.msbackendcore.pricing.domain.services;

import java.math.BigDecimal;
import java.util.UUID;

public interface PricingDomainService {
    void createDefaultPrices(UUID tenantId, UUID variantId, BigDecimal retailPrice, BigDecimal wholesalePrice);
}
