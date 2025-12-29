package codin.msbackendcore.payments.interfaces.dto.payment;

import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateRequest(
        @NotNull UUID tenantId,
        @NotNull UUID orderId,
        @NotNull UUID userId,
        String paymentMethod,
        @NotBlank String paymentStatus,
        @NotBlank String orderNumber,
        @NotNull @Min(1) BigDecimal amount
) {
    public CreatePaymentCommand toCommand() {
        return new CreatePaymentCommand(
                this.tenantId,
                this.orderId,
                this.userId,
                this.paymentMethod,
                this.paymentStatus,
                this.orderNumber,
                this.amount
        );
    }
}
