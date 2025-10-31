package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.AttributeRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AttributeDomainServiceImpl implements AttributeDomainService {
    private final AttributeRepository attributeRepository;

    public AttributeDomainServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Transactional
    @Override
    public Attribute createAttribute(UUID tenantId, String code, String name, DataType dataType) {

        if (Boolean.TRUE.equals(attributeRepository.existsByCodeAndTenantId(code, tenantId)))
            throw new BadRequestException("error.already_exist", new String[]{code}, "code");

        var attribute = Attribute.builder()
                .tenantId(tenantId)
                .code(code)
                .name(name)
                .dataType(dataType)
                .build();

        return attributeRepository.save(attribute);
    }

    @Override
    public Attribute getAttributeById(UUID attributeId) {
        return attributeRepository.findById(attributeId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{attributeId.toString()}, "attributeId")
                );
    }

    @Override
    public List<Attribute> getAttributeByTenantId(UUID tenantId) {
        return attributeRepository.findByTenantId(tenantId);
    }
}
