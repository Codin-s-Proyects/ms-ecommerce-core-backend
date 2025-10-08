package codin.msbackendcore.iam.infrastructure.persistence.jpa;

import codin.msbackendcore.iam.domain.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndTenantId(UUID id, UUID tenantId);
    @Query("""
        SELECT u.id 
        FROM User u 
        WHERE u.tenantId = :tenantId 
          AND u.userType = 'system'
          AND u.active = true
        """)
    Optional<UUID> findSystemUserIdByTenantId(UUID tenantId);

}
