package codin.msbackendcore.pricing.domain.model.queries;

import java.util.UUID;

public record GetAllProductPriceByProductVariantIdQuery(UUID tenantId, UUID productVariantId) {
}
