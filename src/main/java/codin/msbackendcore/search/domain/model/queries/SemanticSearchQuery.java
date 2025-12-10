package codin.msbackendcore.search.domain.model.queries;

import java.util.UUID;

public record SemanticSearchQuery(
        UUID tenantId,
        String query,
        int limit,
        Double distanceThreshold
) {
}
