package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.queries.brand.GetAllBrandByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.brand.BrandCommandService;
import codin.msbackendcore.catalog.domain.services.brand.BrandQueryService;
import codin.msbackendcore.catalog.interfaces.dto.brand.BrandCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.brand.BrandResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/brand")
@Tag(name = "Brand Controller", description = "Gestión de marcas en el catálogo")
public class BrandController {

    private final BrandCommandService brandCommandService;
    private final BrandQueryService brandQueryService;

    public BrandController(BrandCommandService brandCommandService, BrandQueryService brandQueryService) {
        this.brandCommandService = brandCommandService;
        this.brandQueryService = brandQueryService;
    }

    @Operation(summary = "Crear una nueva marca")
    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody BrandCreateRequest req) {

        var command = req.toCommand();

        var saved = brandCommandService.handle(command);

        var response = new BrandResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getSlug(),
                saved.getDescription(),
                saved.getLogoUrl()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Obtener todas las marcas por tenantId")
    @GetMapping("/tenant-id/{tenantId}")
    public ResponseEntity<List<BrandResponse>> getAllBrandByTenantId(@PathVariable UUID tenantId) {

        var query = new GetAllBrandByTenantIdQuery(tenantId);

        var getList = brandQueryService.handle(query);

        var responseList = getList.stream().map(brand ->
                new BrandResponse(
                        brand.getId(),
                        brand.getTenantId(),
                        brand.getName(),
                        brand.getSlug(),
                        brand.getDescription(),
                        brand.getLogoUrl()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

