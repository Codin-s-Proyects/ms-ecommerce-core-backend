package codin.msbackendcore.pricing.interfaces.dto.productdiscount;

import java.util.UUID;

public record ProductDiscountResponse(
        UUID id,
        UUID tenantId,
        UUID productVariantId,
        UUID discountId
) {
}
