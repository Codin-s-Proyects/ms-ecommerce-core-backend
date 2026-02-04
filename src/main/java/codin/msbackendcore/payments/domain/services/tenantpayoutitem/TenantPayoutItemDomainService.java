package codin.msbackendcore.payments.domain.services.tenantpayoutitem;

import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.entities.TenantPayout;
import codin.msbackendcore.payments.domain.model.entities.TenantPayoutItem;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface TenantPayoutItemDomainService {
    TenantPayoutItem createTenantPayoutItem(TenantPayout tenantPayout, SaleCommission saleCommission, UUID orderId,
                                            BigDecimal amount);
}
