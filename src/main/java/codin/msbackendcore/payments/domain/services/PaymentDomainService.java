package codin.msbackendcore.payments.domain.services;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;

import java.util.UUID;

public interface PaymentDomainService {
    Payment createPayment(UUID tenantId, UUID orderId, Double amount);
    Payment confirmPayment(UUID tenantId, String transactionId);
    Payment failPayment(UUID tenantId, String transactionId);
}
