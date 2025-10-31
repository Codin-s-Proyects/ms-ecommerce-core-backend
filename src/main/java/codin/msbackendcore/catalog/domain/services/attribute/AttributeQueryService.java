package codin.msbackendcore.catalog.domain.services.attribute;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.queries.attribute.GetAllAttributeByTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.brand.GetAllBrandByTenantIdQuery;

import java.util.List;

public interface AttributeQueryService {
    List<Attribute> handle(GetAllAttributeByTenantIdQuery query);
}
