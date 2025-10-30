package codin.msbackendcore.catalog.domain.services.categoryattribute;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;

import java.util.UUID;

public interface CategoryAttributeDomainService {
    CategoryAttribute createCategoryAttribute(Category category, Attribute attribute, UUID tenantId);
}
