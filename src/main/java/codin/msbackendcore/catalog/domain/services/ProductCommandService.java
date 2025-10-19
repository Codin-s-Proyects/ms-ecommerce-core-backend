package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.commands.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;

public interface ProductCommandService {
    Product handle(CreateProductCommand command);
}
