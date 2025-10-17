package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.TenantSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantSettingsRepository extends JpaRepository<TenantSettings, UUID> {
    Optional<TenantSettings> findByTenantId(UUID tenantId);
}
