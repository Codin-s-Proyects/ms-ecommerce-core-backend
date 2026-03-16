package codin.msbackendcore.ordering.infrastructure.persistence.jpa.repositories;

import codin.msbackendcore.ordering.domain.model.entities.OrderShippingAddress;
import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserShippingAddressRepository extends JpaRepository<UserShippingAddress, UUID> {
    List<UserShippingAddress> findAllByUserId(UUID userId);
}
