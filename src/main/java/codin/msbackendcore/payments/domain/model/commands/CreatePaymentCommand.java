package codin.msbackendcore.payments.domain.model.commands;

import java.util.UUID;

public record CreatePaymentCommand(
        UUID tenantId,
        UUID orderId,
        Double amount
) {
}
