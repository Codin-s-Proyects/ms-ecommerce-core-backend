package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceCommandService;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing/product-price")
@Tag(name = "Product Price Controller", description = "Operaciones relacionadas con los precios de productos")
public class ProductPriceController {

    private final ProductPriceCommandService productPriceCommandService;

    public ProductPriceController(ProductPriceCommandService productPriceCommandService) {
        this.productPriceCommandService = productPriceCommandService;
    }

    @Operation(summary = "Crear un nuevo precio de producto")
    @PostMapping
    public ResponseEntity<ProductPriceResponse> createProductPrice(@Valid @RequestBody ProductPriceCreateRequest req) {

        var command = req.toCommand();

        var saved = productPriceCommandService.handle(command);

        var response = new ProductPriceResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getProductVariantId(),
                saved.getPriceList().getId(),
                saved.getDiscountPercent(),
                saved.getFinalPrice(),
                saved.getBasePrice(),
                saved.getMinQuantity(),
                saved.getValidFrom(),
                saved.getValidTo()
        );

        return ResponseEntity.status(201).body(response);
    }
}

