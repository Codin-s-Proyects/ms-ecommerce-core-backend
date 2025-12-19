package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;
import codin.msbackendcore.catalog.domain.model.entities.ProductCategory;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryCommandService;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductCategoryCommandServiceImpl implements ProductCategoryCommandService {

    private final ProductCategoryDomainService productCategoryDomainService;
    private final ProductDomainService productDomainService;
    private final CategoryDomainService categoryDomainService;
    private final ExternalCoreService externalCoreService;

    public ProductCategoryCommandServiceImpl(ProductCategoryDomainService productCategoryDomainService, ProductDomainService productDomainService, CategoryDomainService categoryDomainService, ExternalCoreService externalCoreService) {
        this.productCategoryDomainService = productCategoryDomainService;
        this.productDomainService = productDomainService;
        this.categoryDomainService = categoryDomainService;
        this.externalCoreService = externalCoreService;
    }


    @Override
    public ProductCategory handle(CreateProductCategoryCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var category = categoryDomainService.getCategoryById(command.categoryId());
        var product = productDomainService.getProductById(command.productId());

        return productCategoryDomainService.createProductCategory(
                command.tenantId(),
                product,
                category
        );
    }
}
