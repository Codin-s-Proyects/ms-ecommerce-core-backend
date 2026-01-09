package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.events.ProductVariantUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductVariantUpdatedEventHandler {

    private final ExternalSearchService externalSearchService;

    public ProductVariantUpdatedEventHandler(ExternalSearchService externalSearchService) {
        this.externalSearchService = externalSearchService;
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

        log.debug(
                "Execute ProductVariantUpdatedEventHandler | tenant={} variant={}",
                variant.getTenantId(),
                variant.getId()
        );

        externalSearchService.updateProductEmbedding(
                variant.getTenantId(), variant.getId(), product.getName(), categoryName, brandName, product.getDescription(),
                variant.getName(), variant.getAttributes()
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

