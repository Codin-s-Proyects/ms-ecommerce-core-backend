package codin.msbackendcore.ordering.interfaces.dto.orderitem;

import codin.msbackendcore.ordering.domain.model.commands.orderitem.CreateOrderItemCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record OrderItemCreateRequest(
        @NotNull UUID productVariantId,
        @NotBlank String productName,
        @NotBlank String sku,
        @NotNull Map<String, Object> attributes,
        @NotNull @Min(1) Integer quantity,
        @NotNull BigDecimal unitPrice,
        @NotNull BigDecimal discountPercent
) {
    public CreateOrderItemCommand toCommand() {
        return new CreateOrderItemCommand(
                productVariantId,
                productName,
                sku,
                attributes,
                quantity,
                unitPrice,
                discountPercent
        );
    }
}
