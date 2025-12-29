package codin.msbackendcore.payments.domain.model.queries.salecommission;

import java.util.UUID;

public record GetAllSaleCommissionByTenantIdQuery(
        UUID tenantId
) {
}
