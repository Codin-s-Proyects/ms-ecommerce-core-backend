package codin.msbackendcore.payments.interfaces.dto.subscription;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionResponse(
        UUID id,
        UUID tenantId,
        UUID planId,
        String status,
        Instant startedAt,
        Instant nextBillingAt,
        Instant createdAt
) {
}
