package codin.msbackendcore.payments.interfaces.dto.tenantpayout;

import codin.msbackendcore.payments.domain.model.commands.tenantpayout.CreateTenantPayoutCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TenantPayoutCreateRequest(
        @NotNull UUID tenantId,
        @NotBlank String status,
        @NotBlank String payoutMethod,
        @NotBlank String payoutReference,
        @NotBlank String payoutNotes,
        @NotNull UUID executedBy
) {
    public CreateTenantPayoutCommand toCommand() {
        return new CreateTenantPayoutCommand(
                this.tenantId,
                this.status,
                this.payoutMethod,
                this.payoutReference,
                this.payoutNotes,
                this.executedBy
        );
    }
}
