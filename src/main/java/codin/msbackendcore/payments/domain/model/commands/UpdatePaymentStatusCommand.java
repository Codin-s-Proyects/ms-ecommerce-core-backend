package codin.msbackendcore.payments.domain.model.commands;

import java.util.UUID;

public record UpdatePaymentStatusCommand(
        UUID paymentId,
        UUID tenantId,
        String paymentMethod,
        String paymentStatus
) {
}
