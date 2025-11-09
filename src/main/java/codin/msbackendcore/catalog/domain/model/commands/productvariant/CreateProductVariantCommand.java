package codin.msbackendcore.catalog.domain.model.commands.productvariant;

import java.util.Map;
import java.util.UUID;

public record CreateProductVariantCommand(
        UUID tenantId,
        UUID productId,
        String name,
        Map<String, Object> attributes
) {
}
