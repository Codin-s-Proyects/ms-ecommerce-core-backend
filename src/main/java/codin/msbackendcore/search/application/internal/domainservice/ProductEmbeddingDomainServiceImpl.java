package codin.msbackendcore.search.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.core.infrastructure.persistence.mapper.ProductEmbeddingRepositoryJdbc;
import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ProductEmbeddingDomainServiceImpl implements ProductEmbeddingDomainService {

    private final OpenAIEmbeddingClient openAI;
    private final ProductEmbeddingRepositoryJdbc repo;

    public ProductEmbeddingDomainServiceImpl(OpenAIEmbeddingClient openAI, ProductEmbeddingRepositoryJdbc repo) {
        this.openAI = openAI;
        this.repo = repo;
    }

    @Override
    public void generateAndSaveEmbedding(UUID tenantId, ProductVariant variant) {
        String text = buildTextForVariant(variant);
        float[] vector = openAI.embed(text);
        Map<String, Object> metadata = Map.of("name", variant.getName().replace("\"", "'"));
        repo.upsertEmbedding(tenantId, variant.getId(), vector, metadata);
    }

    @Override
    public float[] embedText(String text) {
        return openAI.embed(text);
    }

    private String buildTextForVariant(ProductVariant variant) {
        var p = variant.getProduct();
        return (p.getName() == null ? "" : p.getName()) + " "
                + (p.getDescription() == null ? "" : p.getDescription()) + " "
                + (variant.getName() == null ? "" : variant.getName()) + " "
                + (variant.getAttributes() == null ? "" : variant.getAttributes());
    }
}
