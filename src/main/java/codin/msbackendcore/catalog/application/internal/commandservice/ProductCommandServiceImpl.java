package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final CategoryDomainService categoryDomainService;
    private final BrandDomainService brandDomainService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, CategoryDomainService categoryDomainService, BrandDomainService brandDomainService) {
        this.productDomainService = productDomainService;
        this.categoryDomainService = categoryDomainService;
        this.brandDomainService = brandDomainService;
    }

    @Transactional
    @Override
    public Product handle(CreateProductCommand command) {

        var category = command.categoryId() != null ? categoryDomainService.getCategoryById(command.categoryId()) : null;
        var brand = command.brandId() != null ? brandDomainService.getBrandById(command.brandId()) : null;

        return productDomainService.createProduct(
                command.tenantId(),
                category,
                brand,
                command.name(),
                command.description(),
                command.meta()
        );
    }
}
