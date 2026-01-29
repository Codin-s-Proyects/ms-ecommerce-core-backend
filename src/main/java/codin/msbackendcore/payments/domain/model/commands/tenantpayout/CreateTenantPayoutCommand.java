package codin.msbackendcore.payments.domain.model.commands.tenantpayout;

import java.util.UUID;

public record CreateTenantPayoutCommand(
        UUID tenantId,
        String status,
        String payoutMethod,
        String payoutReference,
        String payoutNotes,
        UUID executedBy
) {
}
