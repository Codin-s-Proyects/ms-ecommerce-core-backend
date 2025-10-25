package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.services.pricelist.PriceListCommandService;
import codin.msbackendcore.pricing.interfaces.dto.pricelist.PriceListCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.pricelist.PriceListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing/price-list")
@Tag(name = "Price List Controller", description = "Operaciones relacionadas con precios de lista")
public class PriceListController {

    private final PriceListCommandService priceListCommandService;

    public PriceListController(PriceListCommandService priceListCommandService) {
        this.priceListCommandService = priceListCommandService;
    }

    @Operation(summary = "Crear un nuevo precio de lista")
    @PostMapping
    public ResponseEntity<PriceListResponse> createPriceList(@Valid @RequestBody PriceListCreateRequest req) {

        var command = req.toCommand();

        var saved = priceListCommandService.handle(command);

        var response = new PriceListResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getCode(),
                saved.getName(),
                saved.getDescription(),
                saved.getCurrencyCode(),
                saved.getValidFrom(),
                saved.getValidTo(),
                saved.getIsActive()
        );

        return ResponseEntity.status(201).body(response);
    }
}

