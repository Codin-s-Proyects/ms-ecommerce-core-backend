package codin.msbackendcore.catalog.domain.model.commands.productvariant;

import java.util.Map;
import java.util.UUID;

public record DeleteProductVariantCommand(
        UUID tenantId,
        UUID productVariantId
) {
}
