package codin.msbackendcore.core.domain.model.commands.tenant;

import codin.msbackendcore.core.domain.model.entities.TenantAddress;
import codin.msbackendcore.core.domain.model.valueobjects.ContactInfo;
import codin.msbackendcore.core.domain.model.valueobjects.LegalInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SocialInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SupportInfo;

import java.util.List;

public record CreateTenantCommand(
        String name,
        String plan,
        String currencyCode,
        String locale,
        LegalInfo legal,
        ContactInfo contact,
        SupportInfo support,
        SocialInfo social,
        List<TenantAddress> addresses
) {
}
