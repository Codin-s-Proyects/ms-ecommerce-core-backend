package codin.msbackendcore.pricing.interfaces.dto.pricelist;

import java.time.Instant;
import java.util.UUID;

public record PriceListResponse(
        UUID id,
        UUID tenantId,
        String code,
        String name,
        String description,
        String currencyCode,
        Instant validFrom,
        Instant validTo,
        Boolean isActive
) {
}
