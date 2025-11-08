package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final BrandDomainService brandDomainService;
    private final ExternalCoreService externalCoreService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, BrandDomainService brandDomainService, ExternalCoreService externalCoreService) {
        this.productDomainService = productDomainService;
        this.brandDomainService = brandDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public Product handle(CreateProductCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var brand = command.brandId() != null ? brandDomainService.getBrandById(command.brandId()) : null;

        return productDomainService.createProduct(
                command.tenantId(),
                brand,
                command.name(),
                command.description(),
                command.meta()
        );
    }
}
