package codin.msbackendcore.pricing.domain.model.commands.productprice;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record UpdateProductPriceCommand(
        UUID productPriceId,
        UUID tenantId,
        UUID priceListId,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validTo
) {
}
