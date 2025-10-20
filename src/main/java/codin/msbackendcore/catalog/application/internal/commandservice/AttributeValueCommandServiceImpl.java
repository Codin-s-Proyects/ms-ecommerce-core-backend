package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.attribute.CreateAttributeCommand;
import codin.msbackendcore.catalog.domain.model.commands.attributevalue.CreateAttributeValueCommand;
import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;
import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeCommandService;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueCommandService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class AttributeValueCommandServiceImpl implements AttributeValueCommandService {

    private final AttributeValueDomainService attributeValueDomainService;
    private final AttributeDomainService attributeDomainService;

    public AttributeValueCommandServiceImpl(AttributeValueDomainService attributeValueDomainService, AttributeDomainService attributeDomainService) {
        this.attributeValueDomainService = attributeValueDomainService;
        this.attributeDomainService = attributeDomainService;
    }

    @Transactional
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
