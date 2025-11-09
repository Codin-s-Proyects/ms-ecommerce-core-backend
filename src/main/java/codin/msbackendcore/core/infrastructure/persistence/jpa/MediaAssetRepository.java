package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaAssetRepository extends JpaRepository<MediaAsset, UUID> {
    List<MediaAsset> findByTenantIdAndEntityTypeAndEntityId(
            UUID tenantId, String entityType, UUID entityId
    );

    boolean existsByTenantIdAndEntityTypeAndEntityIdAndUrl(
            UUID tenantId, String entityType, UUID entityId, String url
    );
}
