package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.entities.TenantAddress;
import codin.msbackendcore.core.domain.model.valueobjects.*;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class TenantDomainServiceImpl implements TenantDomainService {

    private final TenantRepository tenantRepository;

    public TenantDomainServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public Tenant createTenant(String name, String plan, String currencyCode, String locale, LegalInfo legal, ContactInfo contact, SupportInfo support, SocialInfo social, List<TenantAddress> addresses) {
        if (tenantRepository.existsByName(name))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        var savedTenant = new Tenant();
        savedTenant.setSlug(generateSlug(name));
        savedTenant.setName(name);
        savedTenant.setPlan(TenantPlan.valueOf(plan));
        savedTenant.setLegal(legal);
        savedTenant.setContact(contact);
        savedTenant.setSupport(support);
        savedTenant.setSocial(social);
        savedTenant.setCurrencyCode(currencyCode);
        savedTenant.setLocale(locale);

        addresses.forEach(savedTenant::addAddress);

        return tenantRepository.save(savedTenant);
    }

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId")
                );

    }
}
