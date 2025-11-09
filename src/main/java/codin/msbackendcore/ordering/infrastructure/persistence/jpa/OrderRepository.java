package codin.msbackendcore.ordering.infrastructure.persistence.jpa;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByOrderNumberAndTenantId(String orderNumber, UUID tenantId);

    Optional<Order> findByIdAndTenantId(UUID orderId, UUID tenantId);

    List<Order> findByTenantId(UUID tenantId);

    List<Order> findByUserIdAndTenantId(UUID userId, UUID tenantId);

    boolean existsByIdAndTenantId(UUID id, UUID tenantId);

    @Query("""
                SELECT o FROM Order o
                LEFT JOIN FETCH o.statusHistory
                WHERE o.id = :orderId
            """)
    Optional<Order> findByIdWithStatusHistory(UUID orderId);
}
