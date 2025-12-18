package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.attributevalue.CreateAttributeValueCommand;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueCommandService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AttributeValueCommandServiceImpl implements AttributeValueCommandService {

    private final AttributeValueDomainService attributeValueDomainService;
    private final AttributeDomainService attributeDomainService;

    public AttributeValueCommandServiceImpl(AttributeValueDomainService attributeValueDomainService, AttributeDomainService attributeDomainService) {
        this.attributeValueDomainService = attributeValueDomainService;
        this.attributeDomainService = attributeDomainService;
    }

    @Override
    public AttributeValue handle(CreateAttributeValueCommand command) {

        var attribute = attributeDomainService.getAttributeById(command.attributeId());

        return attributeValueDomainService.createAttributeValue(
                attribute,
                command.value(),
                command.label()
        );

    }
}
