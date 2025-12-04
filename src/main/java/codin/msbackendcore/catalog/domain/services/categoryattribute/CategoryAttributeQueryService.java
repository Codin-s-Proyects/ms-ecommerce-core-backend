package codin.msbackendcore.catalog.domain.services.categoryattribute;

import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;
import codin.msbackendcore.catalog.domain.model.queries.categoryattribute.GetAllCategoryAttributeByCategoryQuery;

import java.util.List;

public interface CategoryAttributeQueryService {
    List<CategoryAttribute> handle(GetAllCategoryAttributeByCategoryQuery query);
}
