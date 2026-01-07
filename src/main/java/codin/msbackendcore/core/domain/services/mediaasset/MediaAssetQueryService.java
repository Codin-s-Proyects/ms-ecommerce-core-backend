package codin.msbackendcore.core.domain.services.mediaasset;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.queries.mediaasset.GetMediaAssetsByTenantAndUsageQuery;

import java.util.List;

public interface MediaAssetQueryService {
    List<MediaAsset> handle(GetMediaAssetsByTenantAndUsageQuery query);
}
