package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.services.PaymentCommandService;
import codin.msbackendcore.payments.interfaces.dto.IzipayTokenRequest;
import codin.msbackendcore.payments.interfaces.dto.IzipayTokenResponse;
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

    @Operation(summary = "Obtener token de pago Izipay", description = "Genera un token de pago para Izipay basado en los detalles proporcionados.")
    @PostMapping("/izipay/token")
    public ResponseEntity<IzipayTokenResponse> getIzipayToken(@Valid @RequestBody IzipayTokenRequest req) {

        var command = req.toCommand();

        var getToken = paymentCommandService.handle(command);

        return ResponseEntity.ok(getToken);
    }
}

