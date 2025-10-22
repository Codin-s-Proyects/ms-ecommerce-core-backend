package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductCreatedEventHandler {

    private final ExternalSearchService externalSearchService;

    public ProductCreatedEventHandler(ExternalSearchService externalSearchService) {
        this.externalSearchService = externalSearchService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductCreatedEvent event) {
        for (ProductVariant variant : event.variants()) {

            var product = variant.getProduct();

            try {
                log.debug("Execute ProductCreated Event Handler for variant {}", variant.getId());
                externalSearchService.registerProductEmbedding(event.tenantId(), variant.getId(), product.getName(), product.getDescription(),
                        variant.getName(), variant.getAttributes());
            } catch (Exception ex) {
                log.error("Error generating embedding for variant {}: {}", variant.getId(), ex.getMessage());
            }
        }
        ;
    }
}

