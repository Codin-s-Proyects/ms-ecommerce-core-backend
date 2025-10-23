package codin.msbackendcore.search.domain.services;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductEmbeddingQueryService {
    List<ProductEmbedding> handle(SemanticSearchQuery query);
}
