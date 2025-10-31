package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByBrandAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByCategoryAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products")
@Tag(name = "Product Controller", description = "Operaciones relacionadas con productos")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest req) {

        var command = req.toCommand();

        var saved = productCommandService.handle(command);

        var response = new ProductResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getSlug(),
                saved.getDescription(),
                saved.isHasVariants()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Obtener todos los productos por categoría y tenantId")
    @GetMapping("/category/{categoryId}/tenant-id/{tenantId}")
    public ResponseEntity<List<ProductResponse>> getAllProductByCategory(@PathVariable UUID categoryId, @PathVariable UUID tenantId) {

        var query = new GetAllProductByCategoryAndTenantIdQuery(categoryId, tenantId);

        var getList = productQueryService.handle(query);

        var responseList = getList.stream().map(product ->
                new ProductResponse(
                        product.getId(),
                        product.getTenantId(),
                        product.getName(),
                        product.getSlug(),
                        product.getDescription(),
                        product.isHasVariants()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }

    @Operation(summary = "Obtener todos los productos por marca y tenantId")
    @GetMapping("/brand/{brandId}/tenant-id/{tenantId}")
    public ResponseEntity<List<ProductResponse>> getAllProductByBrand(@PathVariable UUID brandId, @PathVariable UUID tenantId) {

        var query = new GetAllProductByBrandAndTenantIdQuery(brandId, tenantId);

        var getList = productQueryService.handle(query);

        var responseList = getList.stream().map(product ->
                new ProductResponse(
                        product.getId(),
                        product.getTenantId(),
                        product.getName(),
                        product.getSlug(),
                        product.getDescription(),
                        product.isHasVariants()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

