package codin.msbackendcore.payments.domain.model.commands.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand(
        UUID tenantId,
        UUID orderId,
        UUID userId,
        String currencyCode,
        String paymentMethod,
        String paymentStatus,
        String orderNumber,
        BigDecimal amount
) {
}
