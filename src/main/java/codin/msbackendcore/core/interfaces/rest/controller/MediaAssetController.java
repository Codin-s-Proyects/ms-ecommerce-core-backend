package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetCommandService;
import codin.msbackendcore.core.interfaces.dto.mediaasset.CreateMediaAssetRequest;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/core/media-assets")
@Tag(name = "Media Asset", description = "Operaciones relacionadas con los recursos multimedia")
public class MediaAssetController {

    private final MediaAssetCommandService mediaAssetCommandService;

    public MediaAssetController(MediaAssetCommandService mediaAssetCommandService) {
        this.mediaAssetCommandService = mediaAssetCommandService;
    }

    @Operation(summary = "Crear un recurso multimedia", description = "Crea un nuevo recurso multimedia asociado a una entidad específica.")
    @ApiResponse(responseCode = "201", description = "Recurso multimedia creado exitosamente")
    @PostMapping()
    public ResponseEntity<MediaAssetResponse> createMediaAsset(
            @Valid @RequestBody CreateMediaAssetRequest request
    ) {
        var command = request.toCommand();

        var mediaAssetCreated = mediaAssetCommandService.handle(command);

        return ResponseEntity.status(201).body(
                new MediaAssetResponse(
                        mediaAssetCreated.getId(),
                        mediaAssetCreated.getTenantId(),
                        mediaAssetCreated.getEntityType().toString(),
                        mediaAssetCreated.getEntityId(),
                        mediaAssetCreated.getUrl(),
                        mediaAssetCreated.getPublicId(),
                        mediaAssetCreated.getFormat(),
                        mediaAssetCreated.getWidth(),
                        mediaAssetCreated.getHeight(),
                        mediaAssetCreated.getBytes(),
                        mediaAssetCreated.getIsMain(),
                        mediaAssetCreated.getSortOrder(),
                        mediaAssetCreated.getAltText(),
                        mediaAssetCreated.getContext()
                )
        );
    }

    @Operation(summary = "Eliminar un media asset por su Id")
    @DeleteMapping("/{mediaAssetId}/tenant/{tenantId}")
    public ResponseEntity<?> deleteMediaAsset(@PathVariable UUID mediaAssetId, @PathVariable UUID tenantId) {

        var command = new DeleteMediaAssetCommand(mediaAssetId, tenantId);

        mediaAssetCommandService.handle(command);

        return ResponseEntity.status(204).build();
    }
}