package codin.msbackendcore.core.application.internal.eventhandlers;

import codin.msbackendcore.core.application.internal.dto.CatalogEmbeddingDto;
import codin.msbackendcore.core.application.internal.outboundservices.ExternalCatalogService;
import codin.msbackendcore.core.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.core.domain.model.events.MainMediaAssetCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MainMediaAssetCreatedEventHandler {

    private final ExternalCatalogService externalCatalogService;
    private final ExternalSearchService externalSearchService;


    public MainMediaAssetCreatedEventHandler(ExternalCatalogService externalCatalogService, ExternalSearchService externalSearchService) {
        this.externalCatalogService = externalCatalogService;
        this.externalSearchService = externalSearchService;
    }

    @Async("applicationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(MainMediaAssetCreatedEvent event) {
        List<CatalogEmbeddingDto> embeddingCatalogs = externalCatalogService.getProductVariantIdsByProductId(event.entityId(), event.tenantId());

        List<CompletableFuture<Void>> futures = embeddingCatalogs.stream()
                .map(dto ->
                        externalSearchService.registerMainMediaAssetEmbedding(
                                        event.tenantId(), dto.variantId(), event.aiContext(), dto.productName(), dto.categoryName(), dto.brandName(), dto.productDescription(),
                                        dto.variantName(), dto.variantAttributes()
                                )
                                .exceptionally(ex -> {
                                    log.error(
                                            "Error generating media embedding | tenant={} variant={}",
                                            event.tenantId(),
                                            dto.variantId(),
                                            ex
                                    );
                                    return null;
                                })
                ).toList();


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    log.error(
                            "MainMediaAssetCreatedEvent embeddings batch failed | tenant={}",
                            event.tenantId(),
                            ex
                    );
                    return null;
                });
    }
}
