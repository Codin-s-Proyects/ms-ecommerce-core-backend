package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.model.valueobjects.MediaAssetUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MediaAssetRepository extends JpaRepository<MediaAsset, UUID> {
    List<MediaAsset> findAllByTenantIdAndEntityTypeAndEntityId(
            UUID tenantId, EntityType entityType, UUID entityId
    );

    boolean existsByTenantIdAndEntityTypeAndEntityIdAndUrl(
            UUID tenantId, EntityType entityType, UUID entityId, String url
    );

    Optional<MediaAsset> findAllByTenantIdAndId(UUID tenantId, UUID id);

    List<MediaAsset> findAllByTenantIdAndUsage(UUID tenantId, MediaAssetUsage usage);

    @Modifying
    @Query("""
                UPDATE MediaAsset m
                   SET m.isMain = false,
                       m.updatedAt = CURRENT_TIMESTAMP
                 WHERE m.tenantId = :tenantId
                   AND m.entityType = :entityType
                   AND m.entityId = :entityId
                   AND m.isMain = true
            """)
    void unsetMain(
            @Param("tenantId") UUID tenantId,
            @Param("entityType") EntityType entityType,
            @Param("entityId") UUID entityId
    );

}
