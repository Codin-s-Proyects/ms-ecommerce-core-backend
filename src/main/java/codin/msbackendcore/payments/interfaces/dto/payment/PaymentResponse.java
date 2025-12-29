package codin.msbackendcore.payments.interfaces.dto.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID tenantId,
        UUID orderId,
        String paymentMethod,
        String paymentStatus,
        String transactionId,
        BigDecimal amount,
        Instant confirmedAt
) {
}
