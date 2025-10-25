package codin.msbackendcore.pricing.interfaces.dto.productprice;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceResponse(
        UUID id,
        UUID tenantId,
        UUID productVariantId,
        UUID priceListId,
        BigDecimal discountPercent,
        BigDecimal finalPrice,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validFrom,
        Instant validTo
) {
}
