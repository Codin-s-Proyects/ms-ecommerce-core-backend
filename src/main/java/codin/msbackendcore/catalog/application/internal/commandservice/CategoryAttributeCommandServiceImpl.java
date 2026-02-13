package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.categoryattribute.CreateCategoryAttributeCommand;
import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeCommandService;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeDomainService;
import codin.msbackendcore.pricing.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryAttributeCommandServiceImpl implements CategoryAttributeCommandService {

    private final CategoryAttributeDomainService categoryAttributeDomainService;
    private final CategoryDomainService categoryDomainService;
    private final AttributeDomainService attributeDomainService;
    private final ExternalCoreService externalCoreService;

    public CategoryAttributeCommandServiceImpl(CategoryAttributeDomainService categoryAttributeDomainService, CategoryDomainService categoryDomainService, AttributeDomainService attributeDomainService, ExternalCoreService externalCoreService) {
        this.categoryAttributeDomainService = categoryAttributeDomainService;
        this.categoryDomainService = categoryDomainService;
        this.attributeDomainService = attributeDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public CategoryAttribute handle(CreateCategoryAttributeCommand command) {

        var category = categoryDomainService.getCategoryById(command.categoryId(), command.tenantId());
        var attribute = attributeDomainService.getAttributeById(command.attributeId());

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        return categoryAttributeDomainService.createCategoryAttribute(
                category,
                attribute,
                command.tenantId()
        );
    }
}
