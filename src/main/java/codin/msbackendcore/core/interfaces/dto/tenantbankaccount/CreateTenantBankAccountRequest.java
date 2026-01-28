package codin.msbackendcore.core.interfaces.dto.tenantbankaccount;

import codin.msbackendcore.core.domain.model.commands.tenantbankaccount.CreateTenantBankAccountCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTenantBankAccountRequest(
        @NotNull UUID tenantId,
        @NotBlank String bankName,
        @NotBlank String accountType,
        @NotBlank String accountHolder,
        @NotBlank String accountNumber,
        @NotBlank String currencyCode,
        @NotNull boolean isDefault
) {
    public CreateTenantBankAccountCommand toCommand() {

        return new CreateTenantBankAccountCommand(
                tenantId,
                bankName,
                accountType,
                accountHolder,
                accountNumber,
                currencyCode,
                isDefault
        );
    }
}
