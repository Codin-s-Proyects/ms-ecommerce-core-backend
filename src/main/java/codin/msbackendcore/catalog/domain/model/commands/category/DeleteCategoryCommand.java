package codin.msbackendcore.catalog.domain.model.commands.category;

import java.util.UUID;

public record DeleteCategoryCommand(
        UUID tenantId,
        UUID categoryId
) {
}
