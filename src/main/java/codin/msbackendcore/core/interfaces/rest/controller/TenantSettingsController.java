package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.model.queries.tenant_settings.GetTenantSettingsByTenantIdQuery;
import codin.msbackendcore.core.domain.services.TenantSettingsCommandService;
import codin.msbackendcore.core.domain.services.TenantSettingsQueryService;
import codin.msbackendcore.core.interfaces.dto.TenantSettingsResponse;
import codin.msbackendcore.core.interfaces.dto.UpdatePromptRequest;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/core/tenants-settings")
@Tag(name = "Tenant Settings", description = "Operaciones relacionadas con las configuraciones de los tenants")
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
    @GetMapping("tenant/{tenantId}")
    public ResponseEntity<TenantSettingsResponse> getSettings(@PathVariable UUID tenantId) {
        var settings = queryService.handle(new GetTenantSettingsByTenantIdQuery(tenantId))
                .orElseThrow(() -> new NotFoundException("error.not_found", new String[]{tenantId.toString()}, "tenantId"));

        return ResponseEntity.ok(new TenantSettingsResponse(
                settings.getTenantId(),
                settings.getImagePrompt(),
                settings.getComposerPrompt(),
                settings.getUpdatedAt()
        ));
    }

    @Operation(summary = "Actualiza prompts del tenant")
    @ApiResponse(responseCode = "200", description = "Prompt actualizado correctamente")
    @PatchMapping("/prompt")
    public ResponseEntity<TenantSettingsResponse> updateImagePrompt(
            @Valid @RequestBody UpdatePromptRequest request
    ) {
        var command = request.toCommand();

        var updated = commandService.handle(List.of(command));

        return ResponseEntity.ok(
                new TenantSettingsResponse(
                        updated.getTenantId(),
                        updated.getImagePrompt(),
                        updated.getComposerPrompt(),
                        updated.getUpdatedAt()
                )
        );
    }
}