package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;

public interface ProductCommandService {
    Product handle(CreateProductCommand command);
}
