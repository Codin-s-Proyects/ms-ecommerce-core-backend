package codin.msbackendcore.payments.domain.model.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record IzipayTokenPaymentCommand(
        UUID tenantId,
        BigDecimal amount
) {
}
