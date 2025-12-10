package codin.msbackendcore.core.domain.model.commands.tenant;

import codin.msbackendcore.core.domain.model.entities.TenantAddress;
import codin.msbackendcore.core.domain.model.valueobjects.ContactInfo;
import codin.msbackendcore.core.domain.model.valueobjects.LegalInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SocialInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SupportInfo;

import java.util.List;
import java.util.UUID;

public record UpdateTenantCommand(
        UUID tenantId,
        String name,
        String currencyCode,
        String locale,
        String complaintBookUrl,
        LegalInfo legal,
        ContactInfo contact,
        SupportInfo support,
        SocialInfo social,
        List<TenantAddress> addresses
) {
}
