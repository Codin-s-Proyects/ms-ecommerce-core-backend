package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.services.tenantpayout.TenantPayoutCommandService;
import codin.msbackendcore.payments.interfaces.dto.tenantpayout.TenantPayoutCreateRequest;
import codin.msbackendcore.payments.interfaces.dto.tenantpayout.TenantPayoutResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/tenant-payouts")
@Tag(name = "Tenant Payout Controller", description = "Controlador para gestionar los pagos de los comercios.")
public class TenantPayoutController {

    private final TenantPayoutCommandService tenantPayoutCommandService;

    public TenantPayoutController(TenantPayoutCommandService tenantPayoutCommandService) {
        this.tenantPayoutCommandService = tenantPayoutCommandService;
    }

    @Operation(summary = "Registrar un pago", description = "Registra un nuevo pago en el sistema basado en los detalles del pedido proporcionados.")
    @PostMapping
    public ResponseEntity<TenantPayoutResponse> createTenantPayout(@Valid @RequestBody TenantPayoutCreateRequest req) {

        var command = req.toCommand();

        var saved = tenantPayoutCommandService.handle(command);

        var response = new TenantPayoutResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getTotalAmount(),
                saved.getPayoutMethod().name(),
                saved.getPayoutReference(),
                saved.getPayoutNotes(),
                saved.getStatus().name(),
                saved.getExecutedBy(),
                saved.getExecutedAt(),
                saved.getCreatedAt()
        );

        return ResponseEntity.status(201).body(response);
    }
}

