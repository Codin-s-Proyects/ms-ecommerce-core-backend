package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import codin.msbackendcore.search.domain.model.valueobjects.SemanticSearchMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SemanticSearchRequest(
        UUID tenantId,
        @NotBlank String query,
        String mode,
        int limit,
        @DecimalMin("0.0") @DecimalMax("1.0") Double similarityThreshold
) {
    public SemanticSearchQuery toQuery() {
        var threshold = similarityThreshold != null ? similarityThreshold : 0.4;
        var distanceThreshold = 2.0 * (1.0 - threshold);
        return new SemanticSearchQuery(
                tenantId,
                query,
                mode,
                limit,
                distanceThreshold
        );
    }
}
