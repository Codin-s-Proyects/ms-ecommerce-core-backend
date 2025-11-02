package codin.msbackendcore.ordering.interfaces.rest.controller;

import codin.msbackendcore.ordering.domain.services.order.OrderCommandService;
import codin.msbackendcore.ordering.interfaces.dto.order.OrderCreateRequest;
import codin.msbackendcore.ordering.interfaces.dto.order.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ordering/order")
@Tag(name = "Order Controller", description = "Operaciones relacionadas con las órdenes")
public class OrderController {

    private final OrderCommandService orderCommandService;

    public OrderController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @Operation(summary = "Crear una nueva orden", description = "Crea una nueva orden en el sistema")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest req) {

        var command = req.toCommand();

        var saved = orderCommandService.handle(command);

        var response = new OrderResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getUserId(),
                saved.getOrderNumber(),
                saved.getStatus().name(),
                saved.getCurrencyCode(),
                saved.getSubtotal(),
                saved.getDiscountTotal(),
                saved.getTotal(),
                saved.getNotes()
        );

        return ResponseEntity.status(201).body(response);
    }
}

