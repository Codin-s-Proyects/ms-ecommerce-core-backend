package codin.msbackendcore.pricing.domain.services.discount;


import codin.msbackendcore.pricing.domain.model.entities.Discount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface DiscountDomainService {
    Discount createDiscount(UUID tenantId, String name, String description, BigDecimal percentage, Instant startsAt, Instant endsAt);
    Discount getDiscountByTenantAndId(UUID tenantId, UUID discountId);
}