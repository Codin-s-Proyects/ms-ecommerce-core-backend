package codin.msbackendcore.payments.interfaces.dto.tenantpayout;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TenantPayoutResponse(
        UUID id,
        UUID tenantId,
        BigDecimal totalAmount,
        String payoutMethod,
        String payoutReference,
        String payoutNotes,
        String status,
        UUID executedBy,
        Instant executedAt,
        Instant createdAt
) {
}
