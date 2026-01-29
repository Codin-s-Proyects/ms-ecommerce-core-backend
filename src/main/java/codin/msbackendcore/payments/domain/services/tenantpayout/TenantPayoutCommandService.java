package codin.msbackendcore.payments.domain.services.tenantpayout;

import codin.msbackendcore.payments.domain.model.commands.tenantpayout.CreateTenantPayoutCommand;
import codin.msbackendcore.payments.domain.model.entities.TenantPayout;

public interface TenantPayoutCommandService {
    TenantPayout handle(CreateTenantPayoutCommand command);
}
