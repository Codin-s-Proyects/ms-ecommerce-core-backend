package codin.msbackendcore.catalog.domain.services.attributevalue;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;

import java.util.List;

public interface AttributeValueDomainService {
    AttributeValue createAttributeValue(Attribute attribute, String value, String label);
    List<AttributeValue> getAttributeValueByAttribute(Attribute attribute);
}
