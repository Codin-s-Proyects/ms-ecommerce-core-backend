package codin.msbackendcore.pricing.domain.model.commands;

import java.util.UUID;

public record CreateProductDiscountCommand(
        UUID tenantId,
        UUID productVariantId,
        UUID discountId
) {
}
