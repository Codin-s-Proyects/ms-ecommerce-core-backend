package codin.msbackendcore.catalog.domain.services.categoryattribute;

import codin.msbackendcore.catalog.domain.model.commands.categoryattribute.CreateCategoryAttributeCommand;
import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;

public interface CategoryAttributeCommandService {
    CategoryAttribute handle(CreateCategoryAttributeCommand command);
}
