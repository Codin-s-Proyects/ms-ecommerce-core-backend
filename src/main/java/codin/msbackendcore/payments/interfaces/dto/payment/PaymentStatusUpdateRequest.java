package codin.msbackendcore.payments.interfaces.dto.payment;

import codin.msbackendcore.payments.domain.model.commands.UpdatePaymentStatusCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentStatusUpdateRequest(
        @NotNull UUID tenantId,
        String paymentMethod,
        @NotBlank String paymentStatus
) {
    public UpdatePaymentStatusCommand toCommand(UUID paymentId) {
        return new UpdatePaymentStatusCommand(
                paymentId,
                this.tenantId,
                this.paymentMethod,
                this.paymentStatus
        );
    }
}
