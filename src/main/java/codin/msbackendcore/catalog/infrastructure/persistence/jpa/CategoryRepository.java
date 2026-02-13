package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByTenantId(UUID tenantId);
    Optional<Category> findByTenantIdAndId(UUID tenantId, UUID id);
    boolean existsByTenantIdAndName(UUID tenantId, String name);
}
