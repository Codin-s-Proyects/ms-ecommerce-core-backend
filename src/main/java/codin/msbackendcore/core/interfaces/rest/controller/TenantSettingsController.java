package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.services.TenantSettingsCommandService;
import codin.msbackendcore.core.domain.services.TenantSettingsQueryService;
import codin.msbackendcore.core.interfaces.dto.TenantSettingsResponse;
import codin.msbackendcore.core.interfaces.dto.UpdatePromptRequest;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/settings")
public class TenantSettingsController {

    private final TenantSettingsQueryService queryService;
    private final TenantSettingsCommandService commandService;

    public TenantSettingsController(
            TenantSettingsQueryService queryService,
            TenantSettingsCommandService commandService
    ) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @Operation(summary = "Obtiene las configuraciones del tenant")
    @ApiResponse(responseCode = "200", description = "Configuraciones obtenidas correctamente")
    @ApiResponse(responseCode = "404", description = "Tenant no encontrado")
    @GetMapping
    public ResponseEntity<TenantSettingsResponse> getSettings(@PathVariable UUID tenantId) {
        var settings = queryService.getByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));

        return ResponseEntity.ok(new TenantSettingsResponse(
                settings.getTenantId(),
                settings.getImagePrompt(),
                settings.getComposerPrompt(),
                settings.getUpdatedAt()
        ));
    }

    @Operation(summary = "Actualiza el prompt de imagen del tenant")
    @ApiResponse(responseCode = "200", description = "Prompt actualizado correctamente")
    @PatchMapping("/image-prompt")
    public ResponseEntity<TenantSettingsResponse> updateImagePrompt(
            @PathVariable UUID tenantId,
            @Valid @RequestBody UpdatePromptRequest request
    ) {
        var updated = commandService.updateImagePrompt(tenantId, request.value());
        return ResponseEntity.ok(new TenantSettingsResponse(
                updated.getTenantId(),
                updated.getImagePrompt(),
                updated.getComposerPrompt(),
                updated.getUpdatedAt()
        ));
    }

    @Operation(summary = "Actualiza el prompt de texto (composer) del tenant")
    @ApiResponse(responseCode = "200", description = "Prompt actualizado correctamente")
    @PatchMapping("/composer-prompt")
    public ResponseEntity<TenantSettingsResponse> updateComposerPrompt(
            @PathVariable UUID tenantId,
            @Valid @RequestBody UpdatePromptRequest request
    ) {
        var updated = commandService.updateComposerPrompt(tenantId, request.value());
        return ResponseEntity.ok(new TenantSettingsResponse(
                updated.getTenantId(),
                updated.getImagePrompt(),
                updated.getComposerPrompt(),
                updated.getUpdatedAt()
        ));
    }
}