package codin.msbackendcore.catalog.domain.services.product;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductDomainService {
    Product createProduct(UUID tenantId, Brand brand, String name, String description, Map<String, Object> meta);
    Product updateProduct(UUID productId, UUID tenantId, Brand brand, String name, String description, Map<String, Object> meta);
    void updateHasVariant(UUID productId, boolean hasVariant);
    Product getProductById(UUID productId);
    List<Product> getProductsByCategory(UUID tenantId, Category category);
    CursorPage<Product> getProductsByCategory(UUID tenantId, UUID categoryId, CursorPaginationQuery query);
    CursorPage<Product> getProductsByTenantId(UUID tenantId, CursorPaginationQuery query);
    List<Product> getProductsByBrand(UUID tenantId, Brand brand);
    void deleteProductsByTenant(UUID tenantId);
    Product deactivateProduct(UUID productId, UUID tenantId);
}
