package codin.msbackendcore.core.application.internal.dto;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.entities.Tenant;

import java.util.List;

public record TenantWithAssets(
        Tenant tenant,
        List<MediaAsset> mediaAssets
) {
}
