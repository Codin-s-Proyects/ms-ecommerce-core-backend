package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetCommandService;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
@Transactional
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

        if (!isValidEnum(EntityType.class, command.entityType())) {
            throw new BadRequestException("error.bad_request", new String[]{command.entityType()}, "entityType");
        }

        return mediaAssetDomainService.createMediaAsset(
                command.tenantId(),
                EntityType.valueOf(command.entityType()),
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

    @Override
    public void handle(DeleteMediaAssetCommand command) {
        mediaAssetDomainService.deleteMediaAsset(command.tenantId(), command.mediaAssetId());
    }
}
