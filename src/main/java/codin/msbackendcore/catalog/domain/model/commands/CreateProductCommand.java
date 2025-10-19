package codin.msbackendcore.catalog.domain.model.commands;

import java.util.Map;
import java.util.UUID;

public record CreateProductCommand(
    UUID tenantId,
    UUID categoryId,
    UUID brandId,
    String name,
    String description,
    Map<String, Object> meta
) {
}

