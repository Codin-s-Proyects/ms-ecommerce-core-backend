package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.domain.model.events.ProductVariantUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductVariantUpdatedEventHandler {

    private final ExternalSearchService externalSearchService;

    public ProductVariantUpdatedEventHandler(ExternalSearchService externalSearchService) {
        this.externalSearchService = externalSearchService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductVariantUpdatedEvent event) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        var variant = event.variant();

        var product = variant.getProduct();

        String categoryName = (product.getCategories() != null && !product.getCategories().isEmpty())
                ? product.getCategories().stream()
                .map(pc -> pc.getCategory().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "))
                : "";

        var brandName = product.getBrand() != null ? product.getBrand().getName() : "";

        try {
            log.debug("Execute ProductVariantUpdatedEvent Event Handler for variant {}", variant.getId());
            CompletableFuture<Void> future = externalSearchService.updateProductEmbedding(
                    variant.getTenantId(), variant.getId(), product.getName(), categoryName, brandName, product.getDescription(),
                    variant.getName(), variant.getAttributes()
            );
            futures.add(future.exceptionally(ex -> {
                log.error("Error generating embedding for variant {}: {}", variant.getId(), ex.getMessage());
                return null;
            }));
        } catch (Exception ex) {
            log.error("Error registering embedding for variant {}: {}", variant.getId(), ex.getMessage());
        }


        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    log.error("Error in one or more embedding operations: {}", ex.getMessage());
                    return null;
                });
    }
}

