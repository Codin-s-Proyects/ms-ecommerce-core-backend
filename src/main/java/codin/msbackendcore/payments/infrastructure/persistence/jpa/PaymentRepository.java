package codin.msbackendcore.payments.infrastructure.persistence.jpa;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdAndTenantId(UUID id, UUID tenantId);
    Optional<Payment> findByTransactionIdAndTenantId(String transactionId, UUID tenantId);
}
