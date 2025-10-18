package codin.msbackendcore.catalog.interfaces.dto;

import java.util.Map;
import java.util.UUID;

public record ProductSearchResponse(
    UUID id,
    String sku,
    String name,
    String attributes
) {
}
