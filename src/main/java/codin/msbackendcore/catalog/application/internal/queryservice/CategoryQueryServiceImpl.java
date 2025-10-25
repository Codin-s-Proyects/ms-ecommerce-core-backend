package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.queries.category.GetAllCategoryByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryDomainService categoryDomainService;

    public CategoryQueryServiceImpl(CategoryDomainService categoryDomainService) {
        this.categoryDomainService = categoryDomainService;
    }

    @Override
    public List<Category> handle(GetAllCategoryByTenantIdQuery query) {
        return categoryDomainService.getAllCategoryByTenantId(query.tenantId());
    }
}
