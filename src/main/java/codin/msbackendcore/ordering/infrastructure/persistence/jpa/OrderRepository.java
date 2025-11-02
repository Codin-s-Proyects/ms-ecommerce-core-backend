package codin.msbackendcore.ordering.infrastructure.persistence.jpa;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByOrderNumberAndTenantId(String orderNumber, UUID tenantId);
}
