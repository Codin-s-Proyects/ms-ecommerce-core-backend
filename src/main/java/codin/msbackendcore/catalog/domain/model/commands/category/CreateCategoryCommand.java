package codin.msbackendcore.catalog.domain.model.commands.category;

import java.util.UUID;

public record CreateCategoryCommand(
        UUID tenantId,
        UUID parentId,
        String name) {
}
