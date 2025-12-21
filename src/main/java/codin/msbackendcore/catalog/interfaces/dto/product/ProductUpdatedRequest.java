package codin.msbackendcore.catalog.interfaces.dto.product;

import codin.msbackendcore.catalog.domain.model.commands.product.UpdateProductCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;
import java.util.UUID;

public record ProductUpdatedRequest(
        UUID brandId,
        @NotBlank String name,
        String description,
        Map<String, Object> meta
) {
    public UpdateProductCommand toCommand(UUID productId, UUID tenantId) {
        return new UpdateProductCommand(
                productId,
                tenantId,
                brandId,
                name,
                description,
                meta
        );
    }
}
