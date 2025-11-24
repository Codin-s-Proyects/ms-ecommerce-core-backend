package codin.msbackendcore.core.application.internal.domainservice;

import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.valueobjects.EntityType;
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
    public MediaAsset createMediaAsset(UUID tenantId, EntityType entityType, UUID entityId, String url, String publicId, String format, Integer width, Integer height, Long bytes, Boolean isMain, Integer sortOrder, String altText, Map<String, Object> context) {
        if (mediaAssetRepository.existsByTenantIdAndEntityTypeAndEntityIdAndUrl(tenantId, entityType, entityId, url))
            throw new BadRequestException("error.already_exist", new String[]{url}, "url");

        var saved = MediaAsset.builder()
                .tenantId(tenantId)
                .entityType(entityType)
                .entityId(entityId)
                .url(url)
                .publicId(publicId)
                .format(format)
                .width(width)
                .height(height)
                .bytes(bytes)
                .isMain(isMain)
                .sortOrder(sortOrder)
                .altText(altText)
                .context(context)
                .build();

        return mediaAssetRepository.save(saved);
    }

    @Override
    public List<MediaAsset> getAllByEntityTypeAndEntityId(UUID tenantId, EntityType entityType, UUID entityId) {
        return mediaAssetRepository.findAllByTenantIdAndEntityTypeAndEntityId(tenantId, entityType, entityId);
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
