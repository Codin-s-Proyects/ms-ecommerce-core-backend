package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantCommand;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantCommandService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantCommandServiceImpl implements ProductVariantCommandService {

    private final ProductVariantDomainService productVariantDomainService;
    private final ProductDomainService productDomainService;

    public ProductVariantCommandServiceImpl(ProductVariantDomainService productVariantDomainService, ProductDomainService productDomainService) {
        this.productVariantDomainService = productVariantDomainService;
        this.productDomainService = productDomainService;
    }

    @Transactional
    @Override
    public ProductVariant handle(CreateProductVariantCommand command) {

        var product = productDomainService.getProductById(command.productId());

        return productVariantDomainService.createProductVariant(
                command.tenantId(),
                product,
                command.name(),
                command.attributes(),
                command.imageUrl()
        );
    }
}
