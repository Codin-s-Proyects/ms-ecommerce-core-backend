package codin.msbackendcore.core.interfaces.acl;

import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.PlanDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import codin.msbackendcore.core.interfaces.dto.plan.PlanResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoreContextFacade {

    private final TenantDomainService tenantDomainService;
    private final PlanDomainService planDomainService;
    private final MediaAssetDomainService mediaAssetDomainService;

    public CoreContextFacade(TenantDomainService tenantDomainService, PlanDomainService planDomainService, MediaAssetDomainService mediaAssetDomainService) {
        this.tenantDomainService = tenantDomainService;
        this.planDomainService = planDomainService;
        this.mediaAssetDomainService = mediaAssetDomainService;
    }

    public UUID getTenantById(UUID tenantId) {
        var tenant = tenantDomainService.getTenantById(tenantId);
        return tenant.getId();
    }

    public UUID getPlanById(UUID planId) {
        var plan = planDomainService.getPlanById(planId);
        return plan.getId();
    }

    public List<MediaAssetResponse> getMediaAssetByEntityIdAndEntityType(UUID tenantId, EntityType entityType, UUID entityId) {
        return mediaAssetDomainService.getAllByEntityTypeAndEntityId(tenantId, entityType, entityId)
                .stream()
                .map(mediaAsset -> new MediaAssetResponse(
                        mediaAsset.getId(),
                        mediaAsset.getTenantId(),
                        mediaAsset.getEntityType().toString(),
                        mediaAsset.getEntityId(),
                        mediaAsset.getUrl(),
                        mediaAsset.getPublicId(),
                        mediaAsset.getFormat(),
                        mediaAsset.getWidth(),
                        mediaAsset.getHeight(),
                        mediaAsset.getBytes(),
                        mediaAsset.getIsMain(),
                        mediaAsset.getSortOrder(),
                        mediaAsset.getAltText(),
                        mediaAsset.getContext()
                ))
                .toList();
    }

    public PlanResponse getPlanByTenantId(UUID tenantId) {
        var tenant = tenantDomainService.getTenantById(tenantId);

        var plan = tenant.getPlan();

        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getCommissionRate(),
                plan.getMonthlyFee(),
                plan.getOnboardingFee(),
                plan.getGmvMin(),
                plan.getGmvMax(),
                plan.getStatus().toString(),
                plan.getCreatedAt()
        );
    }
}
