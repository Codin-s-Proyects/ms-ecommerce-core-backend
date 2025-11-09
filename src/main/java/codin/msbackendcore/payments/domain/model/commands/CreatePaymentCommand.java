package codin.msbackendcore.payments.domain.model.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand(
        UUID tenantId,
        UUID orderId,
        String orderNumber,
        BigDecimal amount
) {
}
