package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
import codin.msbackendcore.core.domain.model.valueobjects.MediaAssetUsage;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetDomainService;
import codin.msbackendcore.core.infrastructure.persistence.jpa.MediaAssetRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MediaAssetDomainServiceImpl implements MediaAssetDomainService {

    private final MediaAssetRepository mediaAssetRepository;

    public MediaAssetDomainServiceImpl(MediaAssetRepository mediaAssetRepository) {
        this.mediaAssetRepository = mediaAssetRepository;
    }

    @Transactional
    @Override
    public MediaAsset createMediaAsset(UUID tenantId, EntityType entityType, UUID entityId, String url, String publicId, Boolean isMain, Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context, MediaAssetUsage usage, String aiContext) {
        if (mediaAssetRepository.existsByTenantIdAndEntityTypeAndEntityIdAndUrl(tenantId, entityType, entityId, url))
            throw new BadRequestException("error.already_exist", new String[]{url}, "url");

        var saved = MediaAsset.builder()
                .tenantId(tenantId)
                .entityType(entityType)
                .entityId(entityId)
                .url(url)
                .publicId(publicId)
                .isMain(isMain)
                .sortOrder(sortOrder)
                .assetMeta(assetMeta)
                .context(context)
                .usage(usage)
                .aiContext(aiContext)
                .build();

        return mediaAssetRepository.save(saved);
    }

    @Override
    public MediaAsset updateMediaAsset(UUID mediaAssetId, UUID tenantId, Boolean isMain, Integer sortOrder, Map<String, Object> assetMeta, Map<String, Object> context, String aiContext) {
        var mediaAsset = mediaAssetRepository.findAllByTenantIdAndId(tenantId, mediaAssetId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{mediaAssetId.toString()}, "mediaAssetId")
                );

        mediaAsset.setIsMain(isMain);
        mediaAsset.setSortOrder(sortOrder);
        mediaAsset.setAssetMeta(assetMeta);
        mediaAsset.setContext(context);
        mediaAsset.setAiContext(aiContext);

        return mediaAssetRepository.save(mediaAsset);
    }

    @Override
    public MediaAsset getMediaAsset(UUID mediaAssetId, UUID tenantId) {
        return mediaAssetRepository.findAllByTenantIdAndId(tenantId, mediaAssetId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{mediaAssetId.toString()}, "mediaAssetId")
                );
    }

    @Override
    public List<MediaAsset> getAllByEntityTypeAndEntityId(UUID tenantId, EntityType entityType, UUID entityId) {
        return mediaAssetRepository.findAllByTenantIdAndEntityTypeAndEntityId(tenantId, entityType, entityId);
    }

    @Override
    public List<MediaAsset> getAllByTenantIdAndUsage(UUID tenantId, MediaAssetUsage usage) {
        return mediaAssetRepository.findAllByTenantIdAndUsage(tenantId, usage);
    }

    @Override
    public void unsetMainAsset(UUID tenantId, String entityType, UUID entityId) {
        mediaAssetRepository.unsetMain(
                tenantId,
                EntityType.valueOf(entityType),
                entityId
        );
    }

    @Override
    public void deleteMediaAsset(UUID tenantId, UUID mediaAssetId) {
        var mediaAsset = mediaAssetRepository.findAllByTenantIdAndId(tenantId, mediaAssetId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{mediaAssetId.toString()}, "mediaAssetId")
                );

        mediaAssetRepository.delete(mediaAsset);


    }

}
