package codin.msbackendcore.catalog.domain.model.commands.categoryattribute;

import java.util.UUID;

public record CreateCategoryAttributeCommand(
        UUID tenantId,
        UUID categoryId,
        UUID attributeId
) {
}

