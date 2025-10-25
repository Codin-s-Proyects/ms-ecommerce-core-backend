package codin.msbackendcore.pricing.domain.model.commands;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateProductPriceCommand(
        UUID tenantId,
        UUID productVariantId,
        UUID priceListId,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validTo
) {
}
