package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaAssetRepository extends JpaRepository<MediaAsset, UUID> {
    List<MediaAsset> findByTenantIdAndEntityTypeAndEntityId(
            UUID tenantId, EntityType entityType, UUID entityId
    );

    boolean existsByTenantIdAndEntityTypeAndEntityIdAndUrl(
            UUID tenantId, EntityType entityType, UUID entityId, String url
    );
}
