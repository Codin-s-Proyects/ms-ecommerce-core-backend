package codin.msbackendcore.catalog.domain.services.attribute;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;

import java.util.UUID;

public interface AttributeDomainService {
    Attribute createAttribute(UUID tenantId, String code, String name, DataType dataType);
    Attribute getAttributeById(UUID attributeId);
}
