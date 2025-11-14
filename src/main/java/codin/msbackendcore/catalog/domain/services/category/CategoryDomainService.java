package codin.msbackendcore.catalog.domain.services.category;

import codin.msbackendcore.catalog.domain.model.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryDomainService {
    Category createCategory(UUID tenantId, UUID parentId, String name);
    List<Category> getAllCategoryByTenantId(UUID tenantId);
    Category getCategoryById(UUID categoryId);
    void deleteCategory(UUID categoryId, UUID tenantId);
}
