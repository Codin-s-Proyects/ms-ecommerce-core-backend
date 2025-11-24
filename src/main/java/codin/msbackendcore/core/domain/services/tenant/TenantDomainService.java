package codin.msbackendcore.core.domain.services.tenant;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.entities.TenantAddress;
import codin.msbackendcore.core.domain.model.valueobjects.ContactInfo;
import codin.msbackendcore.core.domain.model.valueobjects.LegalInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SocialInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SupportInfo;

import java.util.List;
import java.util.UUID;

public interface TenantDomainService {
    Tenant createTenant(String name, String plan, String currencyCode, String locale, LegalInfo legal, ContactInfo contact, SupportInfo support, SocialInfo social, List<TenantAddress> addresses);
    List<Tenant> getAllTenants();
    Tenant getTenantById(UUID tenantId);
}
