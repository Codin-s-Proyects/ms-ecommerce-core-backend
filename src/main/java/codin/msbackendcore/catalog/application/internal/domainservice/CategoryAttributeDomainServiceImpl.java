package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;
import codin.msbackendcore.catalog.domain.services.categoryattribute.CategoryAttributeDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.CategoryAttributeRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryAttributeDomainServiceImpl implements CategoryAttributeDomainService {

    private final CategoryAttributeRepository categoryAttributeRepository;

    public CategoryAttributeDomainServiceImpl(CategoryAttributeRepository categoryAttributeRepository) {
        this.categoryAttributeRepository = categoryAttributeRepository;
    }

    @Override
    public CategoryAttribute createCategoryAttribute(Category category, Attribute attribute, UUID tenantId) {

        if (categoryAttributeRepository.existsByCategoryAndAttributeAndTenantId(category, attribute, tenantId))
            throw new BadRequestException("error.already_exist", new String[]{category.getName()}, "categoryName");

        var categoryAttribute = CategoryAttribute.builder()
                .tenantId(tenantId)
                .category(category)
                .attribute(attribute)
                .build();

        return categoryAttributeRepository.save(categoryAttribute);
    }

    @Override
    public List<CategoryAttribute> getCategoryAttributesByTenantIdAndCategory(UUID tenantId, Category category) {
        return categoryAttributeRepository.findAllByTenantIdAndCategory(tenantId, category);
    }
}
