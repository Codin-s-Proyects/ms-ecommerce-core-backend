package codin.msbackendcore.core.interfaces.dto.tenantbankaccount;

import java.time.Instant;
import java.util.UUID;

public record TenantBankAccountResponse(
        UUID id,
        UUID tenantId,
        String bankName,
        String accountType,
        String accountHolder,
        String accountLast4,
        String currencyCode,
        String status,
        boolean isDefault,
        Instant createdAt,
        Instant updatedAt
) {}