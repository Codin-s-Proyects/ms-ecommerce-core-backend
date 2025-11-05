package codin.msbackendcore.payments.interfaces.dto;

import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID orderId,
        @NotBlank String orderNumber,
        @NotNull @Min(1) BigDecimal amount
) {
    public CreatePaymentCommand toCommand() {
        return new CreatePaymentCommand(
                this.tenantId,
                this.orderId,
                this.orderNumber,
                this.amount
        );
    }
}
