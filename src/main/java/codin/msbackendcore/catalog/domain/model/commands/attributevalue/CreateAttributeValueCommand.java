package codin.msbackendcore.catalog.domain.model.commands.attributevalue;

import java.util.UUID;

public record CreateAttributeValueCommand(
        UUID attributeId,
        String value,
        String label
) {
}

