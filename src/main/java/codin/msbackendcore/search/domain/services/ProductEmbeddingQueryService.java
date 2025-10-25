package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductEmbeddingQueryService {
    CompletableFuture<List<SemanticSearchDto>> handle(SemanticSearchQuery query);
}
