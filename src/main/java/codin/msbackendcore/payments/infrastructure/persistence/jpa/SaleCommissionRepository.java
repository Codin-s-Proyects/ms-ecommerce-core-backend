package codin.msbackendcore.payments.infrastructure.persistence.jpa;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleCommissionRepository extends JpaRepository<SaleCommission, UUID> {
    boolean existsByOrderId(UUID orderId);
}
