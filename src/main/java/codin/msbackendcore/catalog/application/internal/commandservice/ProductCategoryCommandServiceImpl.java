package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryCommandService;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    public void handle(CreateProductCategoryCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var product = productDomainService.getProductById(command.productId(), command.tenantId());

        Set<UUID> current = productCategoryDomainService.getProductCategoryByProductId(command.tenantId(), command.productId());

        Set<UUID> incoming = command.categoryIds();

        Set<UUID> toInsert = new HashSet<>(incoming);
        toInsert.removeAll(current);

        Set<UUID> toDelete = new HashSet<>(current);
        toDelete.removeAll(incoming);

        if (!toDelete.isEmpty()) {
            productCategoryDomainService.deleteAllByProductIdAndCategoryIds(
                    command.tenantId(),
                    command.productId(),
                    toDelete
            );
        }

        for (UUID categoryId : toInsert) {
            var category = categoryDomainService.getCategoryById(categoryId, command.tenantId());
            productCategoryDomainService.createProductCategory(
                    command.tenantId(),
                    product,
                    category
            );
        }
    }
}
