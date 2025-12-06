package codin.msbackendcore.catalog.application.internal.dto;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductPriceDto(
        UUID id,
        UUID tenantId,
        UUID productVariantId,
        PriceListDto priceList,
        BigDecimal discountPercent,
        BigDecimal finalPrice,
        BigDecimal basePrice,
        Integer minQuantity,
        Instant validFrom,
        Instant validTo
) {
}
