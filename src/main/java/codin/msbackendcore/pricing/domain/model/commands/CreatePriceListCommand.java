package codin.msbackendcore.pricing.domain.model.commands;

import java.time.Instant;
import java.util.UUID;

public record CreatePriceListCommand(
        UUID tenantId,
        String code,
        String name,
        String description,
        String currencyCode,
        Instant validFrom,
        Instant validTo
) {
}
