package codin.msbackendcore.catalog.application.internal.queryservice;

import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;
import codin.msbackendcore.catalog.domain.model.queries.attributevalue.GetAllAttributeValueByAttributeQuery;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueDomainService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeValueQueryServiceImpl implements AttributeValueQueryService {

    private final AttributeValueDomainService attributeValueDomainService;
    private final AttributeDomainService attributeDomainService;

    public AttributeValueQueryServiceImpl(AttributeValueDomainService attributeValueDomainService, AttributeDomainService attributeDomainService) {
        this.attributeValueDomainService = attributeValueDomainService;
        this.attributeDomainService = attributeDomainService;
    }

    @Override
    public List<AttributeValue> handle(GetAllAttributeValueByAttributeQuery query) {

        var attribute = attributeDomainService.getAttributeById(query.attributeId());

        return attributeValueDomainService.getAttributeValueByAttribute(attribute);
    }
}
