package codin.msbackendcore.catalog.domain.services.productcategory;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductCategory;

import java.util.UUID;

public interface ProductCategoryDomainService {
    ProductCategory createProductCategory(UUID tenantId, Product product, Category category);
}
