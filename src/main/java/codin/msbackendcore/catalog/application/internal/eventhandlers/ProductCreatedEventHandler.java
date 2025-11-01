package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ProductCreatedEventHandler {

    private final ExternalSearchService externalSearchService;

    public ProductCreatedEventHandler(ExternalSearchService externalSearchService) {
        this.externalSearchService = externalSearchService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductCreatedEvent event) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (ProductVariant variant : event.variants()) {
            var product = variant.getProduct();
            var categoryName = product.getCategory() != null ? product.getCategory().getName() : "";
            var brandName = product.getBrand() != null ? product.getBrand().getName() : "";

            try {
                log.debug("Execute ProductCreated Event Handler for variant {}", variant.getId());
                CompletableFuture<Void> future = externalSearchService.registerProductEmbedding(
                        event.tenantId(), variant.getId(), product.getName(), categoryName, brandName, product.getDescription(),
                        variant.getName(), variant.getAttributes()
                );
                futures.add(future.exceptionally(ex -> {
                    log.error("Error generating embedding for variant {}: {}", variant.getId(), ex.getMessage());
                    return null;
                }));
            } catch (Exception ex) {
                log.error("Error registering embedding for variant {}: {}", variant.getId(), ex.getMessage());
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    log.error("Error in one or more embedding operations: {}", ex.getMessage());
                    return null;
                });
    }
}

