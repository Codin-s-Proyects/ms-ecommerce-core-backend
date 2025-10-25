package codin.msbackendcore.pricing.domain.model.commands;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateDiscountCommand(
        UUID tenantId,
        String name,
        String description,
        BigDecimal percentage,
        Instant startsAt,
        Instant endsAt
) {
}
