package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.ProductCommandService;
import codin.msbackendcore.catalog.interfaces.dto.ProductCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/products")
public class ProductController {

    private final ProductCommandService productCommandService;

    public ProductController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        Product p = new Product();
        p.setTenantId(req.tenantId());
        p.setName(req.name());
        p.setDescription(req.description());

        if (req.variants() != null) {
            var variants = req.variants().stream().map(vr -> {
                var pv = new ProductVariant();
                pv.setSku(vr.sku());
                pv.setName(vr.name());
                pv.setAttributes(vr.attributes());
                pv.setProduct(p);
                return pv;
            }).toList();
            p.getVariants().addAll(variants);
        }

        var saved = productCommandService.createProduct(p);
        return ResponseEntity.status(201).body(saved);
    }
}

