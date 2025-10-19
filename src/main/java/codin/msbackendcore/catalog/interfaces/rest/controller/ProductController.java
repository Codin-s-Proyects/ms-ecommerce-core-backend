package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.ProductQueryService;
import codin.msbackendcore.catalog.interfaces.dto.ProductCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.ProductResponse;
import codin.msbackendcore.catalog.interfaces.dto.ProductSearchRequest;
import codin.msbackendcore.catalog.interfaces.dto.ProductSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/products")
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
        var saved = productCommandService.createProduct(req);

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

    @Operation(summary = "Búsqueda semántica de productos")
    @PostMapping("/semantic-search")
    public ResponseEntity<List<ProductSearchResponse>> searchProducts(@Valid @RequestBody ProductSearchRequest request) {
        var results = productQueryService.handleSearch(request.tenantId(), request.query(), request.limit());

        var response = results.stream().map(pv -> new ProductSearchResponse(
                pv.getVariantId(),
                pv.getVariantName(),
                pv.getVariantImageUrl(),
                pv.getSku(),
                pv.getProductName(),
                pv.getProductDescription(),
                pv.getPrice()
        )).toList();

        return ResponseEntity.ok(response);
    }
}

