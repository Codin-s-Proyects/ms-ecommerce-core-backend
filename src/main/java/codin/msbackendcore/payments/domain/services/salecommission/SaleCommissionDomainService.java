package codin.msbackendcore.payments.domain.services.salecommission;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface SaleCommissionDomainService {
    SaleCommission createSaleCommission(UUID tenantId, UUID orderId, Payment payment, UUID userId, BigDecimal grossAmount, BigDecimal commissionRate, UUID planId);
}
