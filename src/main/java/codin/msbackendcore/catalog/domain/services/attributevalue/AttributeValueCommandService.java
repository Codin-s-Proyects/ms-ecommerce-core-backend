package codin.msbackendcore.catalog.domain.services.attributevalue;

import codin.msbackendcore.catalog.domain.model.commands.attributevalue.CreateAttributeValueCommand;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;

public interface AttributeValueCommandService {
    AttributeValue handle(CreateAttributeValueCommand command);
}
