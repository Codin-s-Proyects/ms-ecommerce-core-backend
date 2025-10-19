package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.services.CategoryDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.CategoryRepository;
import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.services.TenantDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class CategoryDomainServiceImpl implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    public CategoryDomainServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{categoryId.toString()}, "categoryId")
                );

    }
}
