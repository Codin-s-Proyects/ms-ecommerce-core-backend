package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.domain.services.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.ProductDomainService;
import codin.msbackendcore.shared.domain.events.SimpleDomainEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final SimpleDomainEventPublisher eventPublisher;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, SimpleDomainEventPublisher eventPublisher) {
        this.productDomainService = productDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public Product createProduct(Product product) {
        Product saved = productDomainService.createProduct(product);

        List<UUID> variantIds = saved.getVariants().stream()
                .map(ProductVariant::getId)
                .toList();

        eventPublisher.publish(new ProductCreatedEvent(saved.getTenantId(), variantIds));

        return saved;
    }
}
