package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductDomainService {
    Product createProduct(UUID tenantId, Brand brand, String name, String description, Map<String, Object> meta);
    void updateHasVariant(UUID productId, boolean hasVariant);
    Product getProductById(UUID productId);
    List<Product> getProductsByCategory(UUID tenantId, Category category);
    List<Product> getProductsByBrand(UUID tenantId, Brand brand);
}
