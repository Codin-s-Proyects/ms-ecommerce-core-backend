package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.Product;

public interface ProductCommandService {
    Product createProduct(Product product);
}
