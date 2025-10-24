package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.attribute.CreateAttributeCommand;
import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeCommandService;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class AttributeCommandServiceImpl implements AttributeCommandService {

    private final AttributeDomainService attributeDomainService;

    public AttributeCommandServiceImpl(AttributeDomainService attributeDomainService) {
        this.attributeDomainService = attributeDomainService;
    }

    @Transactional
    @Override
    public Attribute handle(CreateAttributeCommand command) {

        if (!isValidEnum(DataType.class, command.dataType())) {
            throw new BadRequestException("error.bad_request", new String[]{command.dataType()}, "dataType");
        }

        return attributeDomainService.createAttribute(
                command.tenantId(),
                command.code(),
                command.name(),
                DataType.valueOf(command.dataType())
        );

    }
}
