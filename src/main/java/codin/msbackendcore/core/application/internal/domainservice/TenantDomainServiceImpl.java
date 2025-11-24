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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public Tenant updateTenant(UUID tenantId, String name, String currencyCode, String locale, LegalInfo legal, ContactInfo contact, SupportInfo support, SocialInfo social, List<TenantAddress> addresses) {
        var tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));

        if (!tenant.getName().equals(name) && tenantRepository.existsByName(name))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        tenant.setSlug(generateSlug(name));
        tenant.setName(name);
        tenant.setLegal(legal);
        tenant.setContact(contact);
        tenant.setSupport(support);
        tenant.setSocial(social);
        tenant.setCurrencyCode(currencyCode);
        tenant.setLocale(locale);

        updateTenantAddresses(tenant, addresses);

        return tenantRepository.save(tenant);

    }

    private void updateTenantAddresses(Tenant tenant, List<TenantAddress> newAddresses) {
        Map<UUID, TenantAddress> currentById = tenant.getAddresses()
                .stream()
                .filter(a -> a.getId() != null)
                .collect(Collectors.toMap(TenantAddress::getId, a -> a));

        tenant.getAddresses().removeIf(existing ->
                newAddresses.stream().noneMatch(incoming ->
                        incoming.getId() != null && incoming.getId().equals(existing.getId()))
        );

        for (TenantAddress incoming : newAddresses) {
            if (incoming.getId() == null) {
                tenant.addAddress(incoming);
            } else {
                TenantAddress existing = currentById.get(incoming.getId());
                if (existing != null) {
                    existing.setLine1(incoming.getLine1());
                    existing.setCity(incoming.getCity());
                    existing.setCountry(incoming.getCountry());
                }
            }
        }
    }
}
