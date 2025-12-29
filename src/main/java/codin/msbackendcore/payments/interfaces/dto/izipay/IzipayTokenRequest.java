package codin.msbackendcore.payments.interfaces.dto.izipay;

import codin.msbackendcore.payments.domain.model.commands.IzipayTokenPaymentCommand;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record IzipayTokenRequest(
        @NotNull UUID tenantId,
        @NotNull BigDecimal amount
) {
    public IzipayTokenPaymentCommand toCommand() {
        return new IzipayTokenPaymentCommand(
                this.tenantId,
                this.amount
        );
    }
}
