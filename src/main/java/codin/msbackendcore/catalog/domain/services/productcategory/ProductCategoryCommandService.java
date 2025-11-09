package codin.msbackendcore.catalog.domain.services.productcategory;

import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;
import codin.msbackendcore.catalog.domain.model.entities.ProductCategory;

public interface ProductCategoryCommandService {
    ProductCategory handle(CreateProductCategoryCommand command);
}
