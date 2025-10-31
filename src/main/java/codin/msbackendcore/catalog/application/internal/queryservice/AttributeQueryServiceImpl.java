package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.application.internal.domainservice.AttributeDomainServiceImpl;
import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.queries.attribute.GetAllAttributeByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeQueryServiceImpl implements AttributeQueryService {

    private final AttributeDomainServiceImpl attributeDomainService;

    public AttributeQueryServiceImpl(AttributeDomainServiceImpl attributeDomainService) {
        this.attributeDomainService = attributeDomainService;
    }

    @Override
    public List<Attribute> handle(GetAllAttributeByTenantIdQuery query) {
        return attributeDomainService.getAttributeByTenantId(query.tenantId());
    }
}
