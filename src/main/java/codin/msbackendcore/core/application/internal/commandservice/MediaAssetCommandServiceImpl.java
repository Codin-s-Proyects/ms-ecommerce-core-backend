package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetCommandService;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MediaAssetCommandServiceImpl implements MediaAssetCommandService {

    private final MediaAssetDomainService mediaAssetDomainService;
    private final TenantDomainService tenantDomainService;

    public MediaAssetCommandServiceImpl(MediaAssetDomainService mediaAssetDomainService, TenantDomainService tenantDomainService) {
        this.mediaAssetDomainService = mediaAssetDomainService;
        this.tenantDomainService = tenantDomainService;
    }

    @Override
    public MediaAsset handle(CreateMediaAssetCommand command) {

        if (tenantDomainService.getTenantById(command.tenantId()) == null)
            throw new NotFoundException("error.not_found", new String[]{command.tenantId().toString()}, "tenantId");

        return mediaAssetDomainService.createMediaAsset(
                command.tenantId(),
                command.entityType(),
                command.entityId(),
                command.url(),
                command.publicId(),
                command.format(),
                command.width(),
                command.height(),
                command.bytes(),
                command.isMain(),
                command.sortOrder(),
                command.altText(),
                command.context()
        );
    }
}
