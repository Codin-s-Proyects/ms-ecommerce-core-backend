package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductEmbeddingQueryService {
    CompletableFuture<List<ProductEmbedding>> handle(SemanticSearchQuery query);
}
