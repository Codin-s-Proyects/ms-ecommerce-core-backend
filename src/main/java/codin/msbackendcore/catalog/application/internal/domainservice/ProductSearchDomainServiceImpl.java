package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.services.product.ProductSearchDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.dto.ProductSearchResult;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.catalog.infrastructure.persistence.mapper.ProductSearchResultMapper;
import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductSearchDomainServiceImpl implements ProductSearchDomainService {

    private final ProductVariantRepository productVariantRepository;
    private final OpenAIEmbeddingClient openAIEmbeddingClient;
    private final ProductSearchResultMapper productSearchResultMapper;

    public ProductSearchDomainServiceImpl(ProductVariantRepository productVariantRepository, OpenAIEmbeddingClient openAIEmbeddingClient, ProductSearchResultMapper productSearchResultMapper) {
        this.productVariantRepository = productVariantRepository;
        this.openAIEmbeddingClient = openAIEmbeddingClient;
        this.productSearchResultMapper = productSearchResultMapper;
    }

    @Override
    public List<ProductSearchResult> searchSimilarProducts(UUID tenantId, String query, int limit) {
        float[] queryEmbedding = openAIEmbeddingClient.embed(query);
        var rawResults = productVariantRepository.findEnrichedSemanticResults(tenantId, queryEmbedding, limit);

        return productSearchResultMapper.map(rawResults);
    }
}
