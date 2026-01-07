package codin.msbackendcore.core.interfaces.dto.mediaasset;

import codin.msbackendcore.core.domain.model.commands.mediaasset.UpdateMediaAssetCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record UpdateMediaAssetRequest(
        @NotBlank String url,
        @NotBlank String publicId,
        @NotNull Boolean isMain,
        @NotNull Integer sortOrder,
        @NotNull Map<String, Object> assetMeta,
        @NotNull Map<String, Object> context,
        @NotBlank String usage,
        @NotBlank String aiContext
) {
    public UpdateMediaAssetCommand toCommand(UUID mediaAssetId, UUID tenantId) {
        return new UpdateMediaAssetCommand(
                mediaAssetId,
                tenantId,
                url,
                publicId,
                isMain,
                sortOrder,
                assetMeta,
                context,
                usage,
                aiContext
        );
    }
}
