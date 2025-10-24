package codin.msbackendcore.catalog.interfaces.dto.attributevalue;

import java.util.UUID;

public record AttributeValueResponse(
        UUID id,
        UUID attributeId,
        String value,
        String label
) {
}