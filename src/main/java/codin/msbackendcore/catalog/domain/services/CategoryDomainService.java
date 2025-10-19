package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.Category;

import java.util.UUID;

public interface CategoryDomainService {
    Category getCategoryById(UUID categoryId);
}
