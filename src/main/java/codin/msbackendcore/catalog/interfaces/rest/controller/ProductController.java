package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.commands.product.DeleteProductByTenantCommand;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductByBrandAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductPaginatedByCategoryAndTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetAllProductPaginatedByTenantIdQuery;
import codin.msbackendcore.catalog.domain.model.queries.product.GetProductByIdQuery;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductQueryService;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.product.ProductResponse;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;
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
    @GetMapping("/category")
    public ResponseEntity<CursorPage<ProductResponse>> getAllProductByCategory(
            @RequestParam UUID categoryId,
            @RequestParam(required = false) UUID tenantId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false) String sort
    ) {

        var paginationQuery = new CursorPaginationQuery(cursor, limit, sort);
        var query = new GetAllProductPaginatedByCategoryAndTenantIdQuery(categoryId, tenantId, paginationQuery);

        var getPaginatedList = productQueryService.handle(query);

        var productResponseList = getPaginatedList.data().stream().map(product ->
                new ProductResponse(
                        product.getId(),
                        product.getTenantId(),
                        product.getName(),
                        product.getSlug(),
                        product.getDescription(),
                        product.isHasVariants()
                )).toList();

        var paginatedResponse = new CursorPage<>(
                productResponseList,
                getPaginatedList.nextCursor(),
                getPaginatedList.hasMore(),
                getPaginatedList.totalApprox()
        );

        return ResponseEntity.status(200).body(paginatedResponse);
    }

    @Operation(summary = "Obtener todos los productos por categoría y tenantId")
    @GetMapping("/tenant-id/{tenantId}")
    public ResponseEntity<CursorPage<ProductResponse>> getAllProductByTenant(
            @PathVariable UUID tenantId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false) String sort
    ) {

        var paginationQuery = new CursorPaginationQuery(cursor, limit, sort);
        var query = new GetAllProductPaginatedByTenantIdQuery(tenantId, paginationQuery);

        var getPaginatedList = productQueryService.handle(query);

        var productResponseList = getPaginatedList.data().stream().map(product ->
                new ProductResponse(
                        product.getId(),
                        product.getTenantId(),
                        product.getName(),
                        product.getSlug(),
                        product.getDescription(),
                        product.isHasVariants()
                )).toList();

        var paginatedResponse = new CursorPage<>(
                productResponseList,
                getPaginatedList.nextCursor(),
                getPaginatedList.hasMore(),
                getPaginatedList.totalApprox()
        );

        return ResponseEntity.status(200).body(paginatedResponse);
    }

    @Operation(summary = "Obtener todos los productos por marca y tenantId")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {

        var query = new GetProductByIdQuery(productId);

        var getList = productQueryService.handle(query);

        var response =
                new ProductResponse(
                        getList.getId(),
                        getList.getTenantId(),
                        getList.getName(),
                        getList.getSlug(),
                        getList.getDescription(),
                        getList.isHasVariants()
                );

        return ResponseEntity.status(200).body(response);
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

    @Operation(summary = "Eliminar todos los productos por tenantId")
    @DeleteMapping("/tenant-id/{tenantId}")
    public ResponseEntity<Void> deleteProductByTenantId(@PathVariable UUID tenantId) {

        var command = new DeleteProductByTenantCommand(tenantId);

        productCommandService.handle(command);

        return ResponseEntity.noContent().build();
    }
}

