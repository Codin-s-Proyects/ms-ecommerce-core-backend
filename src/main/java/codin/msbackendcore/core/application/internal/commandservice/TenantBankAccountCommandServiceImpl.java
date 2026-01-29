package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.tenantbankaccount.CreateTenantBankAccountCommand;
import codin.msbackendcore.core.domain.model.entities.TenantBankAccount;
import codin.msbackendcore.core.domain.model.valueobjects.AccountType;
import codin.msbackendcore.core.domain.model.valueobjects.BankName;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.domain.services.tenantbankaccount.TenantBankAccountCommandService;
import codin.msbackendcore.core.domain.services.tenantbankaccount.TenantBankAccountDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class TenantBankAccountCommandServiceImpl implements TenantBankAccountCommandService {

    private final TenantBankAccountDomainService tenantBankAccountDomainService;
    private final TenantDomainService tenantDomainService;

    public TenantBankAccountCommandServiceImpl(TenantBankAccountDomainService tenantBankAccountDomainService, TenantDomainService tenantDomainService) {
        this.tenantBankAccountDomainService = tenantBankAccountDomainService;
        this.tenantDomainService = tenantDomainService;
    }

    @Override
    public TenantBankAccount handle(CreateTenantBankAccountCommand command) {
        if (!isValidEnum(AccountType.class, command.accountType()))
            throw new BadRequestException("error.bad_request", new String[]{command.accountType()}, "accountType");

        if (!isValidEnum(BankName.class, command.bankName()))
            throw new BadRequestException("error.bad_request", new String[]{command.bankName()}, "bankName");

        var tenant = tenantDomainService.getTenantById(command.tenantId());

        //TODO: Encrypt account number
        var accountNumberEncrypted = command.accountNumber();

        return tenantBankAccountDomainService.createTenantBankAccount(
                tenant,
                BankName.valueOf(command.bankName()),
                AccountType.valueOf(command.accountType()),
                command.accountHolder(),
                command.accountNumber(),
                accountNumberEncrypted,
                command.currencyCode(),
                command.isDefault()
        );
    }
}
