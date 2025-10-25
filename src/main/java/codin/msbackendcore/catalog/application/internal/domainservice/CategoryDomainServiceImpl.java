package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.CategoryRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class CategoryDomainServiceImpl implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    public CategoryDomainServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(UUID tenantId, UUID parentId, String name, String description) {
        var parentCategory = parentId != null ? getCategoryById(parentId) : null;

        if (categoryRepository.existsByTenantIdAndName(tenantId, name))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        var category = Category.builder()
                .tenantId(tenantId)
                .parent(parentCategory)
                .name(name)
                .slug(generateSlug(name))
                .description(description)
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategoryByTenantId(UUID tenantId) {
        return categoryRepository.findAllByTenantId(tenantId);
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{categoryId.toString()}, "categoryId")
                );

    }
}
