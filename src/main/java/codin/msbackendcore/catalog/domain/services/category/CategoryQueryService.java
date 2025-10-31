package codin.msbackendcore.catalog.domain.services.category;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.queries.category.GetAllCategoryByTenantIdQuery;

import java.util.List;

public interface CategoryQueryService {
    List<Category> handle(GetAllCategoryByTenantIdQuery query);
}
