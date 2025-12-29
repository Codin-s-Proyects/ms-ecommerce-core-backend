package codin.msbackendcore.payments.domain.model.commands.subscription;

import java.time.Instant;
import java.util.UUID;

public record CreateSubscriptionCommand(
        UUID tenantId,
        UUID planId,
        String status,
        Instant startedAt,
        Instant nextBillingAt
) {
}
