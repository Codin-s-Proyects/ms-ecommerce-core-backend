package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantCommandService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.shared.domain.events.SimpleDomainEventPublisher;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantCommandServiceImpl implements ProductVariantCommandService {

    private final ProductVariantDomainService productVariantDomainService;
    private final ProductDomainService productDomainService;
    private final SimpleDomainEventPublisher eventPublisher;
    private final ExternalCoreService externalCoreService;

    public ProductVariantCommandServiceImpl(ProductVariantDomainService productVariantDomainService, ProductDomainService productDomainService, SimpleDomainEventPublisher eventPublisher, ExternalCoreService externalCoreService) {
        this.productVariantDomainService = productVariantDomainService;
        this.productDomainService = productDomainService;
        this.eventPublisher = eventPublisher;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public ProductVariant handle(CreateProductVariantCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var product = productDomainService.getProductById(command.productId());

        var productVariant = productVariantDomainService.createProductVariant(
                command.tenantId(),
                product,
                command.name(),
                command.attributes(),
                command.imageUrl()
        );

        if (!product.isHasVariants()) productDomainService.updateHasVariant(product.getId(), true);

        eventPublisher.publish(new ProductCreatedEvent(command.tenantId(), List.of(productVariant)));

        return productVariant;
    }
}
