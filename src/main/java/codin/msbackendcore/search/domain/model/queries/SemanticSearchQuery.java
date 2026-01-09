package codin.msbackendcore.search.domain.model.queries;

import java.util.UUID;

public record SemanticSearchQuery(
        UUID tenantId,
        String query,
        String mode,
        int limit,
        Double distanceThreshold
) {
}
