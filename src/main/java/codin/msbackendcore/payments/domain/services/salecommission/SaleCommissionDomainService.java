package codin.msbackendcore.payments.domain.services.salecommission;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface SaleCommissionDomainService {
    SaleCommission createSaleCommission(String currencyCode, UUID tenantId, UUID orderId, Payment payment, UUID userId, BigDecimal grossAmount, BigDecimal commissionRate, UUID planId);
    List<SaleCommission> getAllSaleCommissionByTenantId(UUID tenantId);
    List<SaleCommission> getAllUnpaidSaleCommissionsByTenantId(UUID tenantId);
    void markCommissionsAsPaid(SaleCommission commission);
}
