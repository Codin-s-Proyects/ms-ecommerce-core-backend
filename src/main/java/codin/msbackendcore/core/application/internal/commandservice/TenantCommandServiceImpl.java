package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.tenant.CreateTenantCommand;
import codin.msbackendcore.core.domain.model.commands.tenant.UpdateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.services.plan.PlanDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantCommandService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TenantCommandServiceImpl implements TenantCommandService {

    private final TenantDomainService tenantDomainService;
    private final PlanDomainService planDomainService;

    public TenantCommandServiceImpl(TenantDomainService tenantDomainService, PlanDomainService planDomainService) {
        this.tenantDomainService = tenantDomainService;
        this.planDomainService = planDomainService;
    }

    @Override
    public Tenant handle(CreateTenantCommand command) {

        var plan = planDomainService.getPlanById(command.planId());

        return tenantDomainService.createTenant(
                command.name(),
                plan,
                command.complaintBookUrl(),
                command.currencyCode(),
                command.locale(),
                command.legal(),
                command.contact(),
                command.support(),
                command.social(),
                command.addresses()
        );
    }

    @Override
    public Tenant handle(UpdateTenantCommand command) {
        return tenantDomainService.updateTenant(
                command.tenantId(),
                command.name(),
                command.complaintBookUrl(),
                command.currencyCode(),
                command.locale(),
                command.legal(),
                command.contact(),
                command.support(),
                command.social(),
                command.addresses()
        );    }
}
