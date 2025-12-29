package codin.msbackendcore.payments.domain.services.salecommission;

import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.queries.salecommission.GetAllSaleCommissionByTenantIdQuery;

import java.util.List;

public interface SaleCommissionQueryService {
    List<SaleCommission> handle(GetAllSaleCommissionByTenantIdQuery query);
}
