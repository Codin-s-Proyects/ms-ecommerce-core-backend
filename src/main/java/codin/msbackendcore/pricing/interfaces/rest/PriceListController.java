package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.model.queries.GetAllPriceListByTenantIdQuery;
import codin.msbackendcore.pricing.domain.model.queries.GetAllProductPriceByProductVariantIdQuery;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListCommandService;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListQueryService;
import codin.msbackendcore.pricing.interfaces.dto.pricelist.PriceListCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.pricelist.PriceListResponse;
import codin.msbackendcore.pricing.interfaces.dto.productprice.ProductPriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pricing/price-list")
@Tag(name = "Price List Controller", description = "Operaciones relacionadas con precios de lista")
public class PriceListController {

    private final PriceListCommandService priceListCommandService;
    private final PriceListQueryService priceListQueryService;

    public PriceListController(PriceListCommandService priceListCommandService, PriceListQueryService priceListQueryService) {
        this.priceListCommandService = priceListCommandService;
        this.priceListQueryService = priceListQueryService;
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

    @Operation(summary = "Obtener los precios de lista por comercion")
    @GetMapping("/tenant-id/{tenantId}")
    public ResponseEntity<List<PriceListResponse>> getAllPriceListByTenant(@PathVariable UUID tenantId) {

        var query = new GetAllPriceListByTenantIdQuery(tenantId);

        var getList = priceListQueryService.handle(query);

        var responseList = getList.stream().map(priceList ->
                new PriceListResponse(
                        priceList.getId(),
                        priceList.getTenantId(),
                        priceList.getCode(),
                        priceList.getName(),
                        priceList.getDescription(),
                        priceList.getCurrencyCode(),
                        priceList.getValidFrom(),
                        priceList.getValidTo(),
                        priceList.getIsActive()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

