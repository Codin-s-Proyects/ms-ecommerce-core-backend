package codin.msbackendcore.payments.domain.services.tenantpayout;

import codin.msbackendcore.payments.domain.model.entities.TenantPayout;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface TenantPayoutDomainService {
    TenantPayout createTenantPayout(UUID tenantId, BigDecimal totalAmount, PaymentMethod payoutMethod, String payoutReference, String payoutNotes, PaymentStatus status, UUID executedBy);
}
