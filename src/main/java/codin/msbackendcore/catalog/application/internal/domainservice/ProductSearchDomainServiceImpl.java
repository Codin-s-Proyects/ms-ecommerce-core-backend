package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.ProductSearchDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductSearchDomainServiceImpl implements ProductSearchDomainService {

    private final ProductVariantRepository productVariantRepository;
    private final OpenAIEmbeddingClient openAIEmbeddingClient;

    public ProductSearchDomainServiceImpl(ProductVariantRepository productVariantRepository, OpenAIEmbeddingClient openAIEmbeddingClient) {
        this.productVariantRepository = productVariantRepository;
        this.openAIEmbeddingClient = openAIEmbeddingClient;
    }

    @Override
    public List<ProductVariant> searchSimilarProducts(UUID tenantId, String query, int limit) {
        float[] queryEmbedding = openAIEmbeddingClient.embed(query);
        return productVariantRepository.findMostSimilarProducts(tenantId, queryEmbedding, limit);
    }
}
