package codin.msbackendcore.catalog.domain.services.category;

import codin.msbackendcore.catalog.domain.model.commands.category.CreateCategoryCommand;
import codin.msbackendcore.catalog.domain.model.commands.category.DeleteCategoryCommand;
import codin.msbackendcore.catalog.domain.model.entities.Category;

public interface CategoryCommandService {
    Category handle(CreateCategoryCommand command);
    void handle(DeleteCategoryCommand command);
}
