package codin.msbackendcore.catalog.domain.services.attribute;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.queries.attribute.GetAllAttributeByTenantIdQuery;

import java.util.List;

public interface AttributeQueryService {
    List<Attribute> handle(GetAllAttributeByTenantIdQuery query);
}
