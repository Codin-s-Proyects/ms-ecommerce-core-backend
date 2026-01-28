package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.entities.TenantBankAccount;
import codin.msbackendcore.core.domain.model.valueobjects.AccountType;
import codin.msbackendcore.core.domain.model.valueobjects.BankName;
import codin.msbackendcore.core.domain.model.valueobjects.TenantBankAccountStatus;
import codin.msbackendcore.core.domain.services.tenantbankaccount.TenantBankAccountDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.TenantBankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantBankAccountDomainServiceImpl implements TenantBankAccountDomainService {

    private final TenantBankAccountRepository tenantBankAccountRepository;

    public TenantBankAccountDomainServiceImpl(TenantBankAccountRepository tenantBankAccountRepository) {
        this.tenantBankAccountRepository = tenantBankAccountRepository;
    }

    @Override
    public TenantBankAccount createTenantBankAccount(Tenant tenant, BankName bankName, AccountType accountType, String accountHolder, String accountNumber, String accountNumberEncrypted, String currencyCode, boolean isDefault) {
        var saved = TenantBankAccount.builder()
                .tenant(tenant)
                .bankName(bankName)
                .accountType(accountType)
                .accountHolder(accountHolder)
                .accountNumberEncrypted(accountNumberEncrypted)
                .accountLast4(maskAccountNumber(accountNumber))
                .currencyCode(currencyCode)
                .status(TenantBankAccountStatus.ACTIVE)
                .isDefault(isDefault)
                .build();

        return tenantBankAccountRepository.save(saved);
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() <= 4) {
            return accountNumber;
        }
        String last4 = accountNumber.substring(accountNumber.length() - 4);
        return "*".repeat(accountNumber.length() - 4) + last4;
    }
}
