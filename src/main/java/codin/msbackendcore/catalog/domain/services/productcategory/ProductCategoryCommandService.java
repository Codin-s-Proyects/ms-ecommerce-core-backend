package codin.msbackendcore.catalog.domain.services.productcategory;

import codin.msbackendcore.catalog.domain.model.commands.productcategory.CreateProductCategoryCommand;

public interface ProductCategoryCommandService {
    void handle(CreateProductCategoryCommand command);
}
