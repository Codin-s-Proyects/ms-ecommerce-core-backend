package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.DeleteProductByTenantCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.DeleteProductCommand;
import codin.msbackendcore.catalog.domain.model.commands.product.UpdateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;

public interface ProductCommandService {
    Product handle(CreateProductCommand command);
    Product handle(UpdateProductCommand command);
    void handle(DeleteProductByTenantCommand command);
    void handle(DeleteProductCommand command);
}
