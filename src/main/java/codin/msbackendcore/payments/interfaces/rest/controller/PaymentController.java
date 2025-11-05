package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.services.PaymentCommandService;
import codin.msbackendcore.payments.interfaces.dto.PaymentCreateRequest;
import codin.msbackendcore.payments.interfaces.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Controller", description = "API para gestionar pagos")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;

    public PaymentController(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    @Operation(summary = "Crear un nuevo pago", description = "Crea un nuevo pago en el sistema")
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentCreateRequest req) {

        var command = req.toCommand();

        var saved = paymentCommandService.handle(command);

        var response = new PaymentResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getOrderId(),
                saved.getPaymentMethod() == null ? "" : saved.getPaymentMethod().name(),
                saved.getStatus().name(),
                saved.getAmount()
        );

        return ResponseEntity.status(201).body(response);
    }
}

