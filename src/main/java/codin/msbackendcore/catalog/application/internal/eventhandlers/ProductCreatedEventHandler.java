package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

/**
 * Escucha el evento de creación de producto y genera embeddings
 * para cada variante del producto (después del commit).
 */
@Component
public class ProductCreatedEventHandler {

    private final ProductVariantRepository variantRepository;
    private final ProductEmbeddingDomainService embeddingService;

    public ProductCreatedEventHandler(ProductVariantRepository variantRepository,
                                      ProductEmbeddingDomainService embeddingService) {
        this.variantRepository = variantRepository;
        this.embeddingService = embeddingService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductCreatedEvent event) {
        for (UUID variantId : event.variantIds()) {
            variantRepository.findById(variantId).ifPresent(variant -> {
                try {
                    embeddingService.generateAndSaveEmbedding(event.tenantId(), variant);
                } catch (Exception ex) {
                    // 🚨 Importante: el error en embeddings no debe romper el flujo principal.
                    // Puedes loguear o enviar a una cola de retry.
                    System.err.println("⚠️ Error generando embedding para variante " + variantId + ": " + ex.getMessage());
                }
            });
        }
    }
}
