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

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        Product p = new Product();
        p.setTenantId(req.tenantId());
        p.setName(req.name());
        p.setSlug(req.slug());
        p.setDescription(req.description());

        if (req.variants() != null) {
            var variants = req.variants().stream().map(vr -> {
                var pv = new ProductVariant();
                pv.setTenantId(req.tenantId());
                pv.setSku(vr.sku());
                pv.setName(vr.name());
                pv.setAttributes(vr.attributes());
                pv.setProduct(p);
                return pv;
            }).toList();
            p.getVariants().addAll(variants);
        }

        p.setHasVariants(!p.getVariants().isEmpty());

        var saved = productCommandService.createProduct(p);

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
                pv.getId(),
                pv.getSku(),
                pv.getName(),
                pv.getAttributes()
        )).toList();

        return ResponseEntity.ok(response);
    }
}

