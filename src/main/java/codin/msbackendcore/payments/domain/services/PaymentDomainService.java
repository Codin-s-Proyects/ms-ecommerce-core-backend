package codin.msbackendcore.payments.domain.services;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentDomainService {
    Payment createPayment(UUID tenantId, UUID orderId, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus);
    Payment confirmPayment(UUID tenantId, String transactionId);
    Payment failPayment(UUID tenantId, String transactionId);
}
