package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.dto.MediaAssetDto;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.domain.model.events.ProductVariantUpdatedEvent;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CatalogEventHandler {

    private final ExternalSearchService externalSearchService;
    private final ExternalCoreService externalCoreService;

    public CatalogEventHandler(ExternalSearchService externalSearchService, ExternalCoreService externalCoreService) {
        this.externalSearchService = externalSearchService;
        this.externalCoreService = externalCoreService;
    }

    @Async("applicationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductCreatedEvent event) {

        var firstVariant = event.variants().getFirst();

        Optional<String> aiContextOptional = externalCoreService.getMediaAssetByEntityIdAndEntityType(firstVariant.getTenantId(), firstVariant.getProduct().getId(), EntityType.PRODUCT)
                .stream()
                .filter(mediaAsset -> Boolean.TRUE.equals(mediaAsset.isMain()))
                .map(MediaAssetDto::aiContext)
                .filter(Objects::nonNull)
                .findFirst();

        List<CompletableFuture<Void>> futures = event.variants().stream()
                .map(variant -> {
                    var product = variant.getProduct();

                    String categoryName = (product.getCategories() != null && !product.getCategories().isEmpty())
                            ? product.getCategories().stream()
                            .map(pc -> pc.getCategory().getName())
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(" "))
                            : "";

                    var brandName = product.getBrand() != null ? product.getBrand().getName() : "";


                    return externalSearchService.saveProductEmbedding(
                                    event.tenantId(), variant.getId(), product.getName(), categoryName,
                                    brandName, product.getDescription(), variant.getName(),
                                    variant.getAttributes(), aiContextOptional
                            )
                            .exceptionally(ex -> {
                                log.error(
                                        "Error generating product embedding | tenant={} variant={}",
                                        event.tenantId(),
                                        variant.getId(),
                                        ex
                                );
                                return null;
                            });
                })
                .toList();


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    log.error(
                            "ProductCreatedEvent embeddings batch failed | tenant={}",
                            event.tenantId(),
                            ex
                    );
                    return null;
                });
    }

    @Async("applicationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductVariantUpdatedEvent event) {
        var variant = event.variant();
        var product = variant.getProduct();

        String categoryName = (product.getCategories() != null && !product.getCategories().isEmpty())
                ? product.getCategories().stream()
                .map(pc -> pc.getCategory().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "))
                : "";

        var brandName = product.getBrand() != null ? product.getBrand().getName() : "";

        Optional<String> aiContextOptional = externalCoreService.getMediaAssetByEntityIdAndEntityType(variant.getTenantId(), product.getId(), EntityType.PRODUCT)
                .stream()
                .filter(mediaAsset -> Boolean.TRUE.equals(mediaAsset.isMain()))
                .map(MediaAssetDto::aiContext)
                .filter(Objects::nonNull)
                .findFirst();


        externalSearchService.updateProductEmbedding(
                variant.getTenantId(), variant.getId(), product.getName(), categoryName, brandName, product.getDescription(),
                variant.getName(), variant.getAttributes(), aiContextOptional
        ).exceptionally(ex -> {
            log.error(
                    "Error updating product embedding | tenant={} variant={}",
                    variant.getTenantId(),
                    variant.getId(),
                    ex
            );
            return null;
        });
    }
}

