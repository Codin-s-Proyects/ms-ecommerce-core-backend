package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.commands.tenant.CreateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.Tenant;

public interface TenantCommandService {
    Tenant handle(CreateTenantCommand command);
}
