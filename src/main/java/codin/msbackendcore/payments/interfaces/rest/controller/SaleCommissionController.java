package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.model.queries.salecommission.GetAllSaleCommissionByTenantIdQuery;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionQueryService;
import codin.msbackendcore.payments.interfaces.dto.salecommission.SaleCommissionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sale-commission")
@Tag(name = "Sale Commission Controller", description = "API para gestionar la comision de ventas")
public class SaleCommissionController {

    private final SaleCommissionQueryService saleCommissionQueryService;

    public SaleCommissionController(SaleCommissionQueryService saleCommissionQueryService) {
        this.saleCommissionQueryService = saleCommissionQueryService;
    }

    @Operation(summary = "Obtener todas las comisiones de venta por tenant")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<SaleCommissionResponse>> getAllSaleCommissionByTenant(@PathVariable UUID tenantId) {

        var query = new GetAllSaleCommissionByTenantIdQuery(tenantId);

        var getList = saleCommissionQueryService.handle(query);

        var responseList = getList.stream().map(saleCommission ->
                new SaleCommissionResponse(
                        saleCommission.getId(),
                        saleCommission.getTenantId(),
                        saleCommission.getOrderId(),
                        saleCommission.getPayment().getId(),
                        saleCommission.getUserId(),
                        saleCommission.getGrossAmount(),
                        saleCommission.getCommissionAmount(),
                        saleCommission.getMerchantAmount(),
                        saleCommission.getCommissionRate(),
                        saleCommission.getPlanId(),
                        saleCommission.getCreatedAt()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

