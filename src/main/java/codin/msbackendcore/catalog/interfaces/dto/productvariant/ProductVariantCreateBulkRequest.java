package codin.msbackendcore.catalog.interfaces.dto.productvariant;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantBulkCommand;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ProductVariantCreateBulkRequest(
        UUID tenantId,
        UUID productId,
        List<VariantItem> variants
) {
    public CreateProductVariantBulkCommand toCommand() {
        return new CreateProductVariantBulkCommand(
                tenantId,
                productId,
                variants.stream().map(
                        v -> new CreateProductVariantBulkCommand.VariantItemCommand(
                                v.name(),
                                v.attributes(),
                                v.productQuantity()
                        )
                ).toList()
        );
    }

    public record VariantItem(
            String name,
            Map<String, Object> attributes,
            Integer productQuantity
    ) {
    }
}


