package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.commands.productvariant.UpdateProductVariantCommand;

import java.util.Map;
import java.util.UUID;

public record ProductVariantUpdateRequest(
        String name,
        Map<String, Object> attributes,
        Integer productQuantity
) {
    public UpdateProductVariantCommand toCommand(UUID productVariantId) {
        return new UpdateProductVariantCommand(
                productVariantId,
                name,
                attributes,
                productQuantity
        );
    }
}
