package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.model.queries.payment.GetAllPaymentsByUserIdQuery;
import codin.msbackendcore.payments.domain.services.payment.PaymentCommandService;
import codin.msbackendcore.payments.domain.services.payment.PaymentQueryService;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenRequest;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenResponse;
import codin.msbackendcore.payments.interfaces.dto.payment.PaymentCreateRequest;
import codin.msbackendcore.payments.interfaces.dto.payment.PaymentResponse;
import codin.msbackendcore.payments.interfaces.dto.payment.PaymentStatusUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Controller", description = "API para gestionar pagos")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    public PaymentController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    @Operation(summary = "Obtener los atributos de categoria por categoria")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getAllPaymentsByUser(@PathVariable UUID userId) {

        var query = new GetAllPaymentsByUserIdQuery(userId);

        var getList = paymentQueryService.handle(query);

        var responseList = getList.stream().map(payment ->
                new PaymentResponse(
                        payment.getId(),
                        payment.getTenantId(),
                        payment.getOrderId(),
                        payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : "",
                        payment.getStatus().name(),
                        payment.getTransactionId(),
                        payment.getAmount(),
                        payment.getConfirmedAt()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
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

