package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.services.TenantCommandService;
import codin.msbackendcore.core.interfaces.dto.CreateTenantRequest;
import codin.msbackendcore.core.interfaces.dto.TenantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/core/tenant")
@Tag(name = "Tenant", description = "Operaciones relacionadas con Tenants")
public class TenantController {

    private final TenantCommandService commandService;

    public TenantController(
            TenantCommandService commandService
    ) {
        this.commandService = commandService;
    }

    @Operation(summary = "Creacion de un tenant")
    @ApiResponse(responseCode = "201", description = "Tenant creado correctamente")
    @PostMapping()
    public ResponseEntity<TenantResponse> updateImagePrompt(
            @Valid @RequestBody CreateTenantRequest request
    ) {
        var command = request.toCommand();

        var tenantCreated = commandService.handle(command);

        return ResponseEntity.status(201).body(
                new TenantResponse(
                        tenantCreated.getId(),
                        tenantCreated.getSlug(),
                        tenantCreated.getName(),
                        tenantCreated.getPlan().name()
                )
        );
    }
}