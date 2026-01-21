package codin.msbackendcore.ordering.infrastructure.persistence.jpa.repositories;

import codin.msbackendcore.ordering.domain.model.entities.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, UUID> {
}
