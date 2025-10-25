package codin.msbackendcore.pricing.interfaces.dto.discount;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record DiscountResponse(
        UUID id,
        UUID tenantId,
        String name,
        String description,
        BigDecimal percentage,
        Instant startsAt,
        Instant endsAt
) {
}
