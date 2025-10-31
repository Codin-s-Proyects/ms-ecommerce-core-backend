package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;
import codin.msbackendcore.catalog.domain.model.queries.categoryattribute.GetAllCategoryAttributeByCategoryQuery;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeDomainService;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryAttributeQueryServiceImpl implements CategoryAttributeQueryService {

    private final CategoryAttributeDomainService categoryAttributeDomainService;
    private final CategoryDomainService categoryDomainService;

    public CategoryAttributeQueryServiceImpl(CategoryAttributeDomainService categoryAttributeDomainService, CategoryDomainService categoryDomainService) {
        this.categoryAttributeDomainService = categoryAttributeDomainService;
        this.categoryDomainService = categoryDomainService;
    }

    @Override
    public List<CategoryAttribute> handle(GetAllCategoryAttributeByCategoryQuery query) {

        var category = categoryDomainService.getCategoryById(query.categoryId());

        return categoryAttributeDomainService.getCategoryAttributesByTenantIdAndCategory(query.tenantId(), category);
    }
}
