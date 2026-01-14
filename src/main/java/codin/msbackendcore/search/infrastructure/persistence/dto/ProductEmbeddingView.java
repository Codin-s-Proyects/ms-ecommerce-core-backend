package codin.msbackendcore.search.infrastructure.persistence.dto;

import java.util.UUID;

public record ProductEmbeddingView(
        UUID tenantId,
        UUID productVariantId
) {
}
