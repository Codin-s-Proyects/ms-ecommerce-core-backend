package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.services.TenantDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TenantDomainServiceImpl implements TenantDomainService {

    private final TenantRepository tenantRepository;

    public TenantDomainServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public Tenant createTenant(String slug, String name) {

        var savedTenant = new Tenant();
        savedTenant.setSlug(slug);
        savedTenant.setName(name);

        return tenantRepository.save(savedTenant);
    }

    @Override
    public Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId")
                );

    }
}
