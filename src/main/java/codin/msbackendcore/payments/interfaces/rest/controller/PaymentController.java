package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.services.PaymentCommandService;
import codin.msbackendcore.payments.interfaces.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Controller", description = "API para gestionar pagos")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;

    public PaymentController(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    @Operation(summary = "Obtener token de pago Izipay", description = "Genera un token de pago para Izipay basado en los detalles proporcionados.")
    @PostMapping("/izipay/token")
    public ResponseEntity<IzipayTokenResponse> getIzipayToken(@Valid @RequestBody IzipayTokenRequest req) {

        var command = req.toCommand();

        var getToken = paymentCommandService.handle(command);

        return ResponseEntity.ok(getToken);
    }

    @Operation(summary = "Registrar un pago", description = "Registra un nuevo pago en el sistema basado en los detalles del pedido proporcionados.")
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentCreateRequest req) {

        var command = req.toCommand();

        var saved = paymentCommandService.handle(command);

        var response = new PaymentResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getOrderId(),
                saved.getPaymentMethod() != null ? saved.getPaymentMethod().name() : "",
                saved.getTransactionId(),
                saved.getStatus().name(),
                saved.getAmount(),
                saved.getConfirmedAt()
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Actualizar el estado de un pago", description = "Actualizar el estado de un pago existente basado en el ID del pago y los detalles proporcionados.")
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStus(@Valid @RequestBody PaymentStatusUpdateRequest req, @PathVariable("paymentId") UUID paymentId) {

        var command = req.toCommand(paymentId);

        var saved = paymentCommandService.handle(command);

        var response = new PaymentResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getOrderId(),
                saved.getPaymentMethod() != null ? saved.getPaymentMethod().name() : "",
                saved.getTransactionId(),
                saved.getStatus().name(),
                saved.getAmount(),
                saved.getConfirmedAt()
        );

        return ResponseEntity.status(200).body(response);
    }
}

