package codin.msbackendcore.catalog.domain.services.attributevalue;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;

public interface AttributeValueDomainService {
    AttributeValue createAttributeValue(Attribute attribute, String value, String label);
}
