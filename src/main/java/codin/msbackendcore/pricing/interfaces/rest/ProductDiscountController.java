package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.services.productdiscount.ProductDiscountCommandService;
import codin.msbackendcore.pricing.interfaces.dto.productdiscount.ProductDiscountCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.productdiscount.ProductDiscountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing/product-discount")
@Tag(name = "Product Discount Controller", description = "Operaciones relacionadas con descuentos de productos")
public class ProductDiscountController {

    private final ProductDiscountCommandService productDiscountCommandService;

    public ProductDiscountController(ProductDiscountCommandService productDiscountCommandService) {
        this.productDiscountCommandService = productDiscountCommandService;
    }

    @Operation(summary = "Crear un nuevo descuento de producto")
    @PostMapping
    public ResponseEntity<ProductDiscountResponse> createProductDiscount(@Valid @RequestBody ProductDiscountCreateRequest req) {

        var command = req.toCommand();

        var saved = productDiscountCommandService.handle(command);

        var response = new ProductDiscountResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getProductVariantId(),
                saved.getDiscount().getId()
        );

        return ResponseEntity.status(201).body(response);
    }
}

