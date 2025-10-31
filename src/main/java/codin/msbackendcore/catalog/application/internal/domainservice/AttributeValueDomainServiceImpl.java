package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.AttributeValue;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.AttributeValueRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeValueDomainServiceImpl implements AttributeValueDomainService {
    private final AttributeValueRepository attributeValueRepository;

    public AttributeValueDomainServiceImpl(AttributeValueRepository attributeValueRepository) {
        this.attributeValueRepository = attributeValueRepository;
    }

    @Transactional
    @Override
    public AttributeValue createAttributeValue(Attribute attribute, String value, String label) {

        if (Boolean.TRUE.equals(attributeValueRepository.existsByValueAndAttribute(value, attribute)))
            throw new BadRequestException("error.already_exist", new String[]{value}, "value");

        var attributeValue = AttributeValue.builder()
                .attribute(attribute)
                .value(value)
                .label(label)
                .build();

        return attributeValueRepository.save(attributeValue);
    }

    @Override
    public List<AttributeValue> getAttributeValueByAttribute(Attribute attribute) {
        return attributeValueRepository.findAllByAttribute(attribute);
    }
}
