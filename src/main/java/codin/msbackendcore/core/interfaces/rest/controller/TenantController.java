package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.model.queries.tenant.GetAllTenantsQuery;
import codin.msbackendcore.core.domain.services.tenant.TenantCommandService;
import codin.msbackendcore.core.domain.services.tenant.TenantQueryService;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import codin.msbackendcore.core.interfaces.dto.tenant.CreateTenantRequest;
import codin.msbackendcore.core.interfaces.dto.tenant.TenantResponse;
import codin.msbackendcore.core.interfaces.dto.tenant.UpdateTenantRequest;
import codin.msbackendcore.core.interfaces.dto.tenantaddress.TenantAddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/core/tenant")
@Tag(name = "Tenant", description = "Operaciones relacionadas con Tenants")
public class TenantController {

    private final TenantCommandService commandService;
    private final TenantQueryService queryService;

    public TenantController(
            TenantCommandService commandService, TenantQueryService queryService
    ) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Operation(summary = "Obtencion de todos los tenants")
    @ApiResponse(responseCode = "200", description = "Lista de tenants obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        var tenants = queryService.handle(new GetAllTenantsQuery());

        var responseList = tenants.stream().map(
                item -> {

                    var tenant = item.tenant();
                    var mediaAssets = item.mediaAssets();

                    var addressList = tenant.getAddresses().stream().map(
                            address -> new TenantAddressResponse(
                                    address.getId(),
                                    address.getLine1(),
                                    address.getCity(),
                                    address.getCountry()
                            )
                    ).toList();

                    var mediaAssetList = mediaAssets.stream()
                            .map(m -> new MediaAssetResponse(
                                    m.getId(),
                                    m.getTenantId(),
                                    m.getEntityType().toString(),
                                    m.getEntityId(),
                                    m.getUrl(),
                                    m.getPublicId(),
                                    m.getFormat(),
                                    m.getWidth(),
                                    m.getHeight(),
                                    m.getBytes(),
                                    m.getIsMain(),
                                    m.getSortOrder(),
                                    m.getAltText(),
                                    m.getContext()
                            )).toList();

                    return new TenantResponse(
                            tenant.getId(),
                            tenant.getSlug(),
                            tenant.getName(),
                            tenant.getPlan().name(),
                            tenant.getStatus(),
                            tenant.getCurrencyCode(),
                            tenant.getLocale(),
                            tenant.getLegal(),
                            tenant.getContact(),
                            tenant.getSupport(),
                            tenant.getSocial(),
                            addressList,
                            mediaAssetList
                    );
                }).toList();

        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Creacion de un tenant")
    @ApiResponse(responseCode = "201", description = "Tenant creado correctamente")
    @PostMapping()
    public ResponseEntity<TenantResponse> createTenant(
            @Valid @RequestBody CreateTenantRequest request
    ) {
        var command = request.toCommand();

        var tenantCreated = commandService.handle(command);

        var addressList = tenantCreated.getAddresses().stream().map(
                address -> new TenantAddressResponse(
                        address.getId(),
                        address.getLine1(),
                        address.getCity(),
                        address.getCountry()
                )
        ).toList();

        return ResponseEntity.status(201).body(
                new TenantResponse(
                        tenantCreated.getId(),
                        tenantCreated.getSlug(),
                        tenantCreated.getName(),
                        tenantCreated.getPlan().name(),
                        tenantCreated.getStatus(),
                        tenantCreated.getCurrencyCode(),
                        tenantCreated.getLocale(),
                        tenantCreated.getLegal(),
                        tenantCreated.getContact(),
                        tenantCreated.getSupport(),
                        tenantCreated.getSocial(),
                        addressList,
                        null
                )
        );
    }

    @Operation(summary = "Actualizacion de un tenant")
    @ApiResponse(responseCode = "200", description = "Tenant actualizado correctamente")
    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantResponse> updateTenant(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody UpdateTenantRequest request
    ) {
        var command = request.toCommand(tenantId);

        var tenantUpdated = commandService.handle(command);

        var addressList = tenantUpdated.getAddresses().stream().map(
                address -> new TenantAddressResponse(
                        address.getId(),
                        address.getLine1(),
                        address.getCity(),
                        address.getCountry()
                )
        ).toList();

        return ResponseEntity.status(200).body(
                new TenantResponse(
                        tenantUpdated.getId(),
                        tenantUpdated.getSlug(),
                        tenantUpdated.getName(),
                        tenantUpdated.getPlan().name(),
                        tenantUpdated.getStatus(),
                        tenantUpdated.getCurrencyCode(),
                        tenantUpdated.getLocale(),
                        tenantUpdated.getLegal(),
                        tenantUpdated.getContact(),
                        tenantUpdated.getSupport(),
                        tenantUpdated.getSocial(),
                        addressList,
                        null
                )
        );
    }
}