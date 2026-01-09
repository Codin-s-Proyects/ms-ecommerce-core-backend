package codin.msbackendcore.core.application.internal.commandservice;

import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.UpdateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.events.MainMediaAssetCreatedEvent;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.model.valueobjects.MediaAssetUsage;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetCommandService;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.domain.services.tenant.TenantDomainService;
import codin.msbackendcore.shared.domain.events.SimpleDomainEventPublisher;
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
    private final SimpleDomainEventPublisher eventPublisher;

    public MediaAssetCommandServiceImpl(MediaAssetDomainService mediaAssetDomainService, TenantDomainService tenantDomainService, SimpleDomainEventPublisher eventPublisher) {
        this.mediaAssetDomainService = mediaAssetDomainService;
        this.tenantDomainService = tenantDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public MediaAsset handle(CreateMediaAssetCommand command) {

        if (tenantDomainService.getTenantById(command.tenantId()) == null)
            throw new NotFoundException("error.not_found", new String[]{command.tenantId().toString()}, "tenantId");

        if (!isValidEnum(EntityType.class, command.entityType())) {
            throw new BadRequestException("error.bad_request", new String[]{command.entityType()}, "entityType");
        }

        if (!isValidEnum(MediaAssetUsage.class, command.usage())) {
            throw new BadRequestException("error.bad_request", new String[]{command.usage()}, "usage");
        }

        if (Boolean.TRUE.equals(command.isMain())) {
            mediaAssetDomainService.unsetMainAsset(command.tenantId(), command.entityType(), command.entityId());
        }

        var mediaAsset = mediaAssetDomainService.createMediaAsset(
                command.tenantId(),
                EntityType.valueOf(command.entityType()),
                command.entityId(),
                command.url(),
                command.publicId(),
                command.isMain(),
                command.sortOrder(),
                command.assetMeta(),
                command.context(),
                MediaAssetUsage.valueOf(command.usage()),
                command.aiContext()
        );

        if (Boolean.TRUE.equals(command.isMain()) && command.entityType().equals(EntityType.PRODUCT.name()) && !command.aiContext().isEmpty())
                eventPublisher.publish(new MainMediaAssetCreatedEvent(command.tenantId(), command.entityId(), command.aiContext()));


        return mediaAsset;
    }

    @Override
    public MediaAsset handle(UpdateMediaAssetCommand command) {
        if (tenantDomainService.getTenantById(command.tenantId()) == null)
            throw new NotFoundException("error.not_found", new String[]{command.tenantId().toString()}, "tenantId");

        if (!isValidEnum(MediaAssetUsage.class, command.usage())) {
            throw new BadRequestException("error.bad_request", new String[]{command.usage()}, "usage");
        }

        return mediaAssetDomainService.updateMediaAsset(
                command.mediaAssetId(),
                command.tenantId(),
                command.url(),
                command.publicId(),
                command.isMain(),
                command.sortOrder(),
                command.assetMeta(),
                command.context(),
                MediaAssetUsage.valueOf(command.usage()),
                command.aiContext()
        );
    }

    @Override
    public void handle(DeleteMediaAssetCommand command) {
        mediaAssetDomainService.deleteMediaAsset(command.tenantId(), command.mediaAssetId());
    }
}
