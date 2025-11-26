package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SemanticSearchRequest(
        UUID tenantId,
        @NotBlank String query,
        int limit
) {
    public SemanticSearchQuery toQuery() {
        return new SemanticSearchQuery(
                tenantId,
                query,
                limit
        );
    }
}
