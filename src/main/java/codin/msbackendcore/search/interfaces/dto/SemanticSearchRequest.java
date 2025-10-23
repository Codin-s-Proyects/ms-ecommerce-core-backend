package codin.msbackendcore.search.interfaces.dto;

import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SemanticSearchRequest(
        @NotNull UUID tenantId,
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
