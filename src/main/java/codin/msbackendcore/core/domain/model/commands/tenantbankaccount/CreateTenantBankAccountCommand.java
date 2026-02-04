package codin.msbackendcore.core.domain.model.commands.tenantbankaccount;

import java.util.UUID;

public record CreateTenantBankAccountCommand(
        UUID tenantId,
        String bankName,
        String accountType,
        String accountHolder,
        String accountNumber,
        String currencyCode,
        boolean isDefault
) {
}
