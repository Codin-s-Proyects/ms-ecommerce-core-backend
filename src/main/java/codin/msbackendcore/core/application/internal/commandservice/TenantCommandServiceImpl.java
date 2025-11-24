package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.valueobjects.DataType;
import codin.msbackendcore.core.domain.model.commands.tenant.CreateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.valueobjects.TenantPlan;
import codin.msbackendcore.core.domain.services.tenant.TenantCommandService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class TenantCommandServiceImpl implements TenantCommandService {

    private final TenantDomainService tenantDomainService;

    public TenantCommandServiceImpl(TenantDomainService tenantDomainService) {
        this.tenantDomainService = tenantDomainService;
    }

    @Override
    public Tenant handle(CreateTenantCommand command) {

        if (!isValidEnum(TenantPlan.class, command.plan())) {
            throw new BadRequestException("error.bad_request", new String[]{command.plan()}, "plan");
        }

        return tenantDomainService.createTenant(
                command.name(),
                command.plan(),
                command.currencyCode(),
                command.locale(),
                command.legal(),
                command.contact(),
                command.support(),
                command.social(),
                command.addresses()
        );
    }
}
