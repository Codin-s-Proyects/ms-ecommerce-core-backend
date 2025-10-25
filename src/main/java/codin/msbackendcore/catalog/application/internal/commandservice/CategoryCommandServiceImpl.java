package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.category.CreateCategoryCommand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.services.category.CategoryCommandService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryDomainService categoryDomainService;

    public CategoryCommandServiceImpl(CategoryDomainService categoryDomainService) {
        this.categoryDomainService = categoryDomainService;
    }

    @Transactional
    @Override
    public Category handle(CreateCategoryCommand command) {

        return categoryDomainService.createCategory(
                command.tenantId(),
                command.parentId(),
                command.name(),
                command.description()
        );
    }
}
