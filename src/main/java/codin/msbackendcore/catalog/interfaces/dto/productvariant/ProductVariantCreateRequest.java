package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantCommand;

import java.util.Map;
import java.util.UUID;

public record ProductVariantCreateRequest(
        UUID tenantId,
        UUID productId,
        String name,
        Map<String, Object> attributes,
        Integer productQuantity
) {
    public CreateProductVariantCommand toCommand() {
        return new CreateProductVariantCommand(
                tenantId,
                productId,
                name,
                attributes,
                productQuantity
        );
    }
}
