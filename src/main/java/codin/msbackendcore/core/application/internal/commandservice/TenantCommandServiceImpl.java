package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.CreateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.services.TenantCommandService;
import codin.msbackendcore.core.domain.services.TenantDomainService;
import org.springframework.stereotype.Service;

@Service
public class TenantCommandServiceImpl implements TenantCommandService {

    private final TenantDomainService tenantDomainService;

    public TenantCommandServiceImpl(TenantDomainService tenantDomainService) {
        this.tenantDomainService = tenantDomainService;
    }

    @Override
    public Tenant handle(CreateTenantCommand command) {
        return tenantDomainService.createTenant(command.name());
    }
}
