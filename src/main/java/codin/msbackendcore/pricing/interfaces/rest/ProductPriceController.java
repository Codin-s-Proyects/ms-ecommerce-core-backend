package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.model.queries.GetAllProductPriceByProductVariantIdQuery;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceCommandService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceQueryService;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceResponse;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pricing/product-price")
@Tag(name = "Product Price Controller", description = "Operaciones relacionadas con los precios de productos")
public class ProductPriceController {

    private final ProductPriceCommandService productPriceCommandService;
    private final ProductPriceQueryService productPriceQueryService;

    public ProductPriceController(ProductPriceCommandService productPriceCommandService, ProductPriceQueryService productPriceQueryService) {
        this.productPriceCommandService = productPriceCommandService;
        this.productPriceQueryService = productPriceQueryService;
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

    @Operation(summary = "Actualizar el precio de producto")
    @PutMapping("/{productPriceId}/tenant/{tenantId}")
    public ResponseEntity<ProductPriceResponse> updateProductPrice(@Valid @RequestBody ProductPriceUpdateRequest req, @PathVariable UUID productPriceId, @PathVariable UUID tenantId) {

        var command = req.toCommand(tenantId, productPriceId);

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

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Obtener todos los precios de un variante de producto")
    @GetMapping("/product-variant/{productVariantId}/tenant-id/{tenantId}")
    public ResponseEntity<List<ProductPriceResponse>> getAllProductPriceByProductVariant(@PathVariable UUID tenantId, @PathVariable UUID productVariantId) {

        var query = new GetAllProductPriceByProductVariantIdQuery(tenantId, productVariantId);

        var getList = productPriceQueryService.handle(query);

        var responseList = getList.stream().map(productPrice ->
                new ProductPriceResponse(
                        productPrice.getId(),
                        productPrice.getTenantId(),
                        productPrice.getProductVariantId(),
                        productPrice.getPriceList().getId(),
                        productPrice.getDiscountPercent(),
                        productPrice.getFinalPrice(),
                        productPrice.getBasePrice(),
                        productPrice.getMinQuantity(),
                        productPrice.getValidFrom(),
                        productPrice.getValidTo()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }

}

