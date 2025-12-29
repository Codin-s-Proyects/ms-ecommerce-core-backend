package codin.msbackendcore.payments.domain.model.queries.payment;

import java.util.UUID;

public record GetAllPaymentsByUserIdQuery(
        UUID userId
) {
}
