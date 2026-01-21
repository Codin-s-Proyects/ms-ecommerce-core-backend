package codin.msbackendcore.ordering.infrastructure.persistence.jpa;

import codin.msbackendcore.ordering.domain.model.entities.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, UUID> {
}
