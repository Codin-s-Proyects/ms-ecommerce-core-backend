package codin.msbackendcore.catalog.domain.services.attribute;

import codin.msbackendcore.catalog.domain.model.commands.attribute.CreateAttributeCommand;
import codin.msbackendcore.catalog.domain.model.entities.Attribute;

public interface AttributeCommandService {
    Attribute handle(CreateAttributeCommand command);
}
