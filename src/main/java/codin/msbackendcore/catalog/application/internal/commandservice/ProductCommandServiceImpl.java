package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.DeleteProductByTenantCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.DeleteProductCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.UpdateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryDomainService;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final ProductVariantDomainService productVariantDomainService;
    private final ProductCategoryDomainService productCategoryDomainService;
    private final BrandDomainService brandDomainService;
    private final ExternalCoreService externalCoreService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, ProductVariantDomainService productVariantDomainService, ProductCategoryDomainService productCategoryDomainService, BrandDomainService brandDomainService, ExternalCoreService externalCoreService) {
        this.productDomainService = productDomainService;
        this.productVariantDomainService = productVariantDomainService;
        this.productCategoryDomainService = productCategoryDomainService;
        this.brandDomainService = brandDomainService;
        this.externalCoreService = externalCoreService;
    }

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

    @Override
    public Product handle(UpdateProductCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var brand = command.brandId() != null ? brandDomainService.getBrandById(command.brandId()) : null;

        return productDomainService.updateProduct(
                command.productId(),
                command.tenantId(),
                brand,
                command.name(),
                command.description(),
                command.meta()
        );
    }

    @Override
    public void handle(DeleteProductByTenantCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        productDomainService.deleteProductsByTenant(command.tenantId());
    }

    @Override
    public void handle(DeleteProductCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var product = productDomainService.deactivateProduct(command.productId(), command.tenantId());

        for(var variant : product.getVariants()){
            productVariantDomainService.deactivateProductVariant(product.getTenantId(), variant.getId());
        }

        for(var category : product.getCategories()){
            productCategoryDomainService.deleteProductCategory(product.getTenantId(), category.getId());
        }

        //TODO: Validar si desactivar todas las entidades o eliminar


    }
}
