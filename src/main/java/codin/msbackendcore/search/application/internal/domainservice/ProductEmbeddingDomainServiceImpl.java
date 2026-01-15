package codin.msbackendcore.search.application.internal.domainservice;

import codin.msbackendcore.search.application.internal.builders.EmbeddingTextBuilder;
import codin.msbackendcore.search.application.internal.outboundservices.embedding.OpenAIEmbeddingClient;
import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import codin.msbackendcore.search.domain.model.valueobjects.ProductEmbeddingSourceType;
import codin.msbackendcore.search.domain.model.valueobjects.SemanticSearchMode;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.infrastructure.persistence.dto.ProductEmbeddingView;
import codin.msbackendcore.search.infrastructure.persistence.jpa.ProductEmbeddingRepository;
import codin.msbackendcore.search.infrastructure.persistence.jpa.ProductEmbeddingRepositoryJdbc;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.toVectorString;

@Service
public class ProductEmbeddingDomainServiceImpl implements ProductEmbeddingDomainService {

    private final OpenAIEmbeddingClient openAI;
    private final ProductEmbeddingRepositoryJdbc repo;
    private final ProductEmbeddingRepository productEmbeddingRepository;

    public ProductEmbeddingDomainServiceImpl(OpenAIEmbeddingClient openAI, ProductEmbeddingRepositoryJdbc repo, ProductEmbeddingRepository productEmbeddingRepository) {
        this.openAI = openAI;
        this.repo = repo;
        this.productEmbeddingRepository = productEmbeddingRepository;
    }

    @Override
    @Transactional
    public CompletableFuture<Void> generateAndSaveEmbedding(UUID tenantId, UUID variantId, String productName, String categoryName, String brandName, String productDescription,
                                                            String variantName, Map<String, Object> variantAttributes, Optional<String> aiContext, boolean isCreated) {
        String textOnly = EmbeddingTextBuilder.
                buildTextOnly(productName, categoryName, brandName, productDescription, variantName, variantAttributes);

        CompletableFuture<Void> textOnlyFuture =
                saveEmbedding(
                        textOnly,
                        tenantId,
                        variantId,
                        ProductEmbeddingSourceType.TEXT_ONLY
                );

        String compositeText = aiContext
                .map(imageText ->
                        EmbeddingTextBuilder.buildComposite(textOnly, imageText)
                )
                .orElse(textOnly);


        CompletableFuture<Void> compositeFuture =
                saveEmbedding(
                        compositeText,
                        tenantId,
                        variantId,
                        ProductEmbeddingSourceType.COMPOSITE
                );

        if (isCreated)
            aiContext.ifPresent(imageText -> saveEmbedding(
                    imageText,
                    tenantId,
                    variantId,
                    ProductEmbeddingSourceType.IMAGE_ONLY
            ));

        return CompletableFuture.allOf(textOnlyFuture, compositeFuture);
    }

    @Override
    @Transactional
    public CompletableFuture<Void> generateAndSaveEmbeddingWithImageCase(
            UUID tenantId, UUID variantId, String aiContext, String productName, String categoryName, String brandName, String productDescription,
            String variantName, Map<String, Object> variantAttributes
    ) {
        String imageText = EmbeddingTextBuilder
                .buildFromAiContext(aiContext);

        saveEmbedding(
                imageText,
                tenantId,
                variantId,
                ProductEmbeddingSourceType.IMAGE_ONLY
        );

        String textOnly = EmbeddingTextBuilder.buildTextOnly(productName, categoryName, brandName, productDescription, variantName, variantAttributes);

        return saveEmbedding(
                EmbeddingTextBuilder.buildComposite(textOnly, imageText),
                tenantId,
                variantId,
                ProductEmbeddingSourceType.COMPOSITE
        );
    }

    @Override
    @Bulkhead(name = "semanticSearchBulkhead", type = Bulkhead.Type.SEMAPHORE)
    public CompletableFuture<List<ProductEmbeddingView>> semanticSearch(UUID tenantId, String query, int limit, SemanticSearchMode mode, Double distanceThreshold) {
        ProductEmbeddingSourceType sourceType = switch (mode) {
            case IMAGE -> ProductEmbeddingSourceType.IMAGE_ONLY;
            case TEXT, COMPOSITE -> ProductEmbeddingSourceType.COMPOSITE;
        };

        return openAI.embedAsync(query)
                .thenApply(queryEmbedding -> productEmbeddingRepository.search(tenantId, queryEmbedding, sourceType.name(), limit, distanceThreshold));
    }

    @Override
    public List<String> findSemanticDetails(UUID tenantId, UUID[] variantIds) {
        return productEmbeddingRepository.findSemanticDetails(tenantId, variantIds);
    }


    private CompletableFuture<Void> saveEmbedding(String embeddingText, UUID tenantId, UUID variantId, ProductEmbeddingSourceType sourceType) {
        return openAI.embedAsync(embeddingText)
                .thenAccept(vector -> {
                    Map<String, Object> metadata = new HashMap<>(Map.of("length", embeddingText.length()));
                    metadata.put("sourceType", sourceType.name());

                    repo.saveEmbedding(tenantId, variantId, toVectorString(vector), metadata, sourceType);
                });
    }
}
