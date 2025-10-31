package codin.msbackendcore.catalog.domain.services.attributevalue;

import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;
import codin.msbackendcore.catalog.domain.model.queries.attributevalue.GetAllAttributeValueByAttributeQuery;

import java.util.List;

public interface AttributeValueQueryService {
    List<AttributeValue> handle(GetAllAttributeValueByAttributeQuery query);
}
