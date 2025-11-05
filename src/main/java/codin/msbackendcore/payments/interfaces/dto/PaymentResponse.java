package codin.msbackendcore.payments.interfaces.dto;

import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID tenantId,
        UUID orderId,
        String paymentMethod,
        String status,
        Double amount
) {
}
