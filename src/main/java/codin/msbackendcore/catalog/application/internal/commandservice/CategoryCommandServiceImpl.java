package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.category.CreateCategoryCommand;
import codin.msbackendcore.catalog.domain.model.commands.category.DeleteCategoryCommand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.services.category.CategoryCommandService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryDomainService categoryDomainService;
    private final ExternalCoreService externalCoreService;

    public CategoryCommandServiceImpl(CategoryDomainService categoryDomainService, ExternalCoreService externalCoreService) {
        this.categoryDomainService = categoryDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public Category handle(CreateCategoryCommand command) {

        if (command.tenantId() != null && !externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        return categoryDomainService.createCategory(
                command.tenantId(),
                command.parentId(),
                command.name()
        );
    }

    @Override
    public void handle(DeleteCategoryCommand command) {
        categoryDomainService.deleteCategory(command.categoryId(), command.tenantId());
    }
}
