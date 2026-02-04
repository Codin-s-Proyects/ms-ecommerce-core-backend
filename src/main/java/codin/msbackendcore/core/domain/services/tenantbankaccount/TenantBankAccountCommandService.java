package codin.msbackendcore.core.domain.services.tenantbankaccount;

import codin.msbackendcore.core.domain.model.commands.tenantbankaccount.CreateTenantBankAccountCommand;
import codin.msbackendcore.core.domain.model.entities.TenantBankAccount;

public interface TenantBankAccountCommandService {
    TenantBankAccount handle(CreateTenantBankAccountCommand command);
}
