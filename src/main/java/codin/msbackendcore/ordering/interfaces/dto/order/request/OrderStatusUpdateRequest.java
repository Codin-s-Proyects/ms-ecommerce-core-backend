package codin.msbackendcore.ordering.interfaces.dto.order.request;

import codin.msbackendcore.ordering.domain.model.commands.order.UpdateOrderStatusCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderStatusUpdateRequest(
        @NotNull UUID tenantId,
        @NotBlank String orderStatus
) {
    public UpdateOrderStatusCommand toCommand(UUID orderId) {

        return new UpdateOrderStatusCommand(
                tenantId,
                orderId,
                orderStatus
        );
    }
}
