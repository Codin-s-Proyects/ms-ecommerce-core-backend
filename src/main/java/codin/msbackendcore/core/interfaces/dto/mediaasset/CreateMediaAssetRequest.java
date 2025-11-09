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
        @NotBlank String publicId,
        String format,
        Integer width,
        Integer height,
        Long bytes,
        @NotNull Boolean isMain,
        @NotNull Integer sortOrder,
        String altText,
        @NotNull Map<String, Object> context
) {
    public CreateMediaAssetCommand toCommand() {
        return new CreateMediaAssetCommand(
                tenantId,
                entityType,
                entityId,
                url,
                publicId,
                format,
                width,
                height,
                bytes,
                isMain,
                sortOrder,
                altText,
                context
        );
    }
}
