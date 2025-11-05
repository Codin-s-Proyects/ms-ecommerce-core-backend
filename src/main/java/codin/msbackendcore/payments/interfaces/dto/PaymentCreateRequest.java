package codin.msbackendcore.payments.interfaces.dto;

import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID orderId,
        @NotNull @Min(1) Double amount
) {
    public CreatePaymentCommand toCommand() {
        return new CreatePaymentCommand(
                this.tenantId,
                this.orderId,
                this.amount
        );
    }
}
