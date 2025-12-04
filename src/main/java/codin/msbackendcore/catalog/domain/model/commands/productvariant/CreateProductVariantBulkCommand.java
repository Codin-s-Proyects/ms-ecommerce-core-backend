package codin.msbackendcore.catalog.domain.model.commands.productvariant;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CreateProductVariantBulkCommand(
        UUID tenantId,
        UUID productId,
        List<VariantItemCommand> variants
) {
    public record VariantItemCommand(
            String name,
            Map<String, Object> attributes,
            Integer productQuantity
    ) {}
}
