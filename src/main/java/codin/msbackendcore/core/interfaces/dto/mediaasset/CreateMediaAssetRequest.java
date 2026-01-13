package codin.msbackendcore.core.interfaces.dto.mediaasset;

import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record CreateMediaAssetRequest(
        @NotNull UUID tenantId,
        @NotBlank String entityType,
        @NotNull UUID entityId,
        @NotBlank String url,
        String publicId,
        @NotNull Boolean isMain,
        @NotNull Integer sortOrder,
        Map<String, Object> assetMeta,
        Map<String, Object> context,
        @NotBlank String usage,
        String aiContext
) {
    public CreateMediaAssetCommand toCommand() {
        return new CreateMediaAssetCommand(
                tenantId,
                entityType,
                entityId,
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
