package codin.msbackendcore.payments.domain.model.queries;

import java.util.UUID;

public record GetAllPaymentsByUserIdQuery(
        UUID userId
) {
}
