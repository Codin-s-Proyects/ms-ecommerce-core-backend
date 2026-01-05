package codin.msbackendcore.catalog.application.internal.dto;

import java.time.Instant;
import java.util.UUID;

public record PriceListDto(
        UUID id,
        UUID tenantId,
        String code,
        String name,
        String description,
        String currencyCode,
        Instant validFrom,
        Instant validTo,
        String status
) {
}
