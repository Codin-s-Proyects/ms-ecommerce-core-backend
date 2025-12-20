package codin.msbackendcore.catalog.domain.model.commands.productvariant;

import java.util.Map;
import java.util.UUID;

public record UpdateProductVariantCommand(
        UUID productVariantId,
        String name,
        Map<String, Object> attributes,
        int productQuantity
) {
}
