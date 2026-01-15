package codin.msbackendcore.payments.interfaces.rest.controller;

import codin.msbackendcore.payments.domain.services.subscription.SubscriptionCommandService;
import codin.msbackendcore.payments.interfaces.dto.subscription.SubscriptionCreateRequest;
import codin.msbackendcore.payments.interfaces.dto.subscription.SubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscription Controller", description = "API para gestionar suscripciones")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @Operation(summary = "Registrar un pago", description = "Registra un nuevo pago en el sistema basado en los detalles del pedido proporcionados.")
    @PostMapping
    public ResponseEntity<SubscriptionResponse> createSubscription(@Valid @RequestBody SubscriptionCreateRequest req) {

        var command = req.toCommand();

        var saved = subscriptionCommandService.handle(command);

        var response = new SubscriptionResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getPlanId(),
                saved.getStatus().name(),
                saved.getStartedAt(),
                saved.getNextBillingAt(),
                saved.getCreatedAt()
        );

        return ResponseEntity.status(201).body(response);
    }
}

