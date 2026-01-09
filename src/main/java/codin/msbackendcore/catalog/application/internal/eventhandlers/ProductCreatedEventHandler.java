package codin.msbackendcore.catalog.application.internal.eventhandlers;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalSearchService;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductCreatedEventHandler {

    private final ExternalSearchService externalSearchService;

    public ProductCreatedEventHandler(ExternalSearchService externalSearchService) {
        this.externalSearchService = externalSearchService;
    }

    @Async("applicationTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductCreatedEvent event) {
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

                    return externalSearchService.registerProductEmbedding(
                                    event.tenantId(), variant.getId(), product.getName(), categoryName,
                                    brandName, product.getDescription(), variant.getName(),
                                    variant.getAttributes()
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
}

