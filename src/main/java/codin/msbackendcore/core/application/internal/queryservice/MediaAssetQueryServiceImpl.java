package codin.msbackendcore.core.application.internal.queryservice;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.queries.mediaasset.GetMediaAssetsByTenantAndUsageQuery;
import codin.msbackendcore.core.domain.model.valueobjects.MediaAssetUsage;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetQueryService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class MediaAssetQueryServiceImpl implements MediaAssetQueryService {

    private final MediaAssetDomainService mediaAssetDomainService;

    public MediaAssetQueryServiceImpl(MediaAssetDomainService mediaAssetDomainService) {
        this.mediaAssetDomainService = mediaAssetDomainService;
    }

    @Override
    public List<MediaAsset> handle(GetMediaAssetsByTenantAndUsageQuery query) {

        if (!isValidEnum(MediaAssetUsage.class, query.usage())) {
            throw new BadRequestException("error.bad_request", new String[]{query.usage()}, "usage");
        }

        return this.mediaAssetDomainService.getAllByTenantIdAndUsage(query.tenantId(), MediaAssetUsage.valueOf(query.usage()));
    }
}
