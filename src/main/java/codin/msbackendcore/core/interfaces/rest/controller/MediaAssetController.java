package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;
import codin.msbackendcore.core.domain.model.queries.mediaasset.GetMediaAssetsByTenantAndUsageQuery;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetCommandService;
import codin.msbackendcore.core.domain.services.mediaasset.MediaAssetQueryService;
import codin.msbackendcore.core.interfaces.dto.mediaasset.CreateMediaAssetRequest;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import codin.msbackendcore.core.interfaces.dto.mediaasset.UpdateMediaAssetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/core/media-assets")
@Tag(name = "Media Asset", description = "Operaciones relacionadas con los recursos multimedia")
public class MediaAssetController {

    private final MediaAssetCommandService mediaAssetCommandService;
    private final MediaAssetQueryService mediaAssetQueryService;

    public MediaAssetController(MediaAssetCommandService mediaAssetCommandService, MediaAssetQueryService mediaAssetQueryService) {
        this.mediaAssetCommandService = mediaAssetCommandService;
        this.mediaAssetQueryService = mediaAssetQueryService;
    }

    @Operation(summary = "Obtencion todos los media assets por tenant")
    @ApiResponse(responseCode = "200", description = "Media assets obtenidos correctamente")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<MediaAssetResponse>> getMediaAssetByTenant(@PathVariable UUID tenantId, @RequestParam String usage) {
        var handleResponse = mediaAssetQueryService.handle(new GetMediaAssetsByTenantAndUsageQuery(tenantId,usage));

        var listResponse = handleResponse.stream().map(this::transformEntityToMediaAssetResponse).toList();

        return ResponseEntity.ok(listResponse);
    }


    @Operation(summary = "Crear un recurso multimedia", description = "Crea un nuevo recurso multimedia asociado a una entidad específica.")
    @ApiResponse(responseCode = "201", description = "Recurso multimedia creado exitosamente")
    @PostMapping()
    public ResponseEntity<MediaAssetResponse> createMediaAsset(
            @Valid @RequestBody CreateMediaAssetRequest request
    ) {
        var command = request.toCommand();

        var mediaAssetCreated = mediaAssetCommandService.handle(command);

        return ResponseEntity.status(201).body(transformEntityToMediaAssetResponse(mediaAssetCreated));
    }

    @Operation(summary = "Actualizar un recurso multimedia", description = "Actualizar un recurso multimedia asociado a una entidad específica.")
    @ApiResponse(responseCode = "200", description = "Recurso multimedia actualizado exitosamente")
    @PutMapping("/{mediaAssetId}/tenant/{tenantId}")
    public ResponseEntity<MediaAssetResponse> updateMediaAsset(
            @Valid @RequestBody UpdateMediaAssetRequest request,
            @PathVariable("mediaAssetId") UUID mediaAssetId,
            @PathVariable("tenantId") UUID tenantId
    ) {
        var command = request.toCommand(mediaAssetId, tenantId);

        var mediaAssetUpdated = mediaAssetCommandService.handle(command);

        return ResponseEntity.status(200).body(transformEntityToMediaAssetResponse(mediaAssetUpdated));
    }

    @Operation(summary = "Eliminar un media asset por su Id")
    @DeleteMapping("/{mediaAssetId}/tenant/{tenantId}")
    public ResponseEntity<?> deleteMediaAsset(@PathVariable UUID mediaAssetId, @PathVariable UUID tenantId) {

        var command = new DeleteMediaAssetCommand(mediaAssetId, tenantId);

        mediaAssetCommandService.handle(command);

        return ResponseEntity.status(204).build();
    }

    private MediaAssetResponse transformEntityToMediaAssetResponse(MediaAsset entity) {
        return new MediaAssetResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getEntityType().toString(),
                entity.getEntityId(),
                entity.getUrl(),
                entity.getPublicId(),
                entity.getIsMain(),
                entity.getSortOrder(),
                entity.getAssetMeta(),
                entity.getContext(),
                entity.getUsage().name(),
                entity.getAiContext()
        );
    }
}