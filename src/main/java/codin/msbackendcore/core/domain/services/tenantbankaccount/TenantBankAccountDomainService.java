package codin.msbackendcore.core.domain.services.tenantbankaccount;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.entities.TenantBankAccount;
import codin.msbackendcore.core.domain.model.valueobjects.AccountType;
import codin.msbackendcore.core.domain.model.valueobjects.BankName;

public interface TenantBankAccountDomainService {
    TenantBankAccount createTenantBankAccount(Tenant tenant, BankName bankName, AccountType accountType, String accountHolder, String accountNumber, String accountNumberEncrypted, String currencyCode, boolean isDefault);
}
