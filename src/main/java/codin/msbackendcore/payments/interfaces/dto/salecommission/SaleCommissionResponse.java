package codin.msbackendcore.payments.interfaces.dto.salecommission;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SaleCommissionResponse(
        UUID id,
        UUID tenantId,
        UUID orderId,
        UUID paymentId,
        UUID userId,
        BigDecimal grossAmount,
        BigDecimal commissionAmount,
        BigDecimal merchantAmount,
        BigDecimal commissionRate,
        UUID planId,
        Instant createdAt
) {
}
