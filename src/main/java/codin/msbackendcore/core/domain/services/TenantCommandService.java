package codin.msbackendcore.core.domain.services;

import codin.msbackendcore.core.domain.model.commands.CreateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.Tenant;

public interface TenantCommandService {
    Tenant handle(CreateTenantCommand command);
}
