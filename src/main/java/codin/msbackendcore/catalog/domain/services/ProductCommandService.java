package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.interfaces.dto.ProductCreateRequest;

public interface ProductCommandService {
    Product createProduct(ProductCreateRequest request);
}
