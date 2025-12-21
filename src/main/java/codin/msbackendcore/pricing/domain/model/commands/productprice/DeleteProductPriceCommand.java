package codin.msbackendcore.pricing.domain.model.commands.productprice;

import java.util.UUID;

public record DeleteProductPriceCommand(
        UUID productPriceId,
        UUID tenantId
) {
}
