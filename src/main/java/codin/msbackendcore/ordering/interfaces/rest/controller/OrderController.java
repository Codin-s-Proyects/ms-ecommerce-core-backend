package codin.msbackendcore.ordering.interfaces.rest.controller;

import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByTenantIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByUserIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetOrderByIdQuery;
import codin.msbackendcore.ordering.domain.services.order.OrderCommandService;
import codin.msbackendcore.ordering.domain.services.order.OrderQueryService;
import codin.msbackendcore.ordering.interfaces.dto.order.OrderCreateRequest;
import codin.msbackendcore.ordering.interfaces.dto.order.OrderResponse;
import codin.msbackendcore.ordering.interfaces.dto.order.OrderStatusUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ordering/order")
@Tag(name = "Order Controller", description = "Operaciones relacionadas con las órdenes")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    public OrderController(OrderCommandService orderCommandService, OrderQueryService orderQueryService) {
        this.orderCommandService = orderCommandService;
        this.orderQueryService = orderQueryService;
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

    @Operation(summary = "Actualizar el estado de una orden", description = "Actualiza el estado de una orden existente utilizando su ID")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest req, @PathVariable UUID orderId) {

        var command = req.toCommand(orderId);

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

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una orden por ID", description = "Obtiene los detalles de una orden específica utilizando su ID y el ID del tenant")
    @GetMapping("/{orderId}/tenant/{tenantId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID orderId, @PathVariable UUID tenantId) {

        var query = new GetOrderByIdQuery(orderId, tenantId);

        var order = orderQueryService.handle(query);

        var orderResponse = new OrderResponse(
                order.getId(),
                order.getTenantId(),
                order.getUserId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getCurrencyCode(),
                order.getSubtotal(),
                order.getDiscountTotal(),
                order.getTotal(),
                order.getNotes()
        );

        return ResponseEntity.ok(orderResponse);
    }

    @Operation(summary = "Obtener todas las órdenes por ID de tenant", description = "Obtiene una lista de todas las órdenes asociadas a un ID de tenant específico")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<OrderResponse>> getAllByTenantId(@PathVariable UUID tenantId) {

        var query = new GetAllOrdersByTenantIdQuery(tenantId);

        var getList = orderQueryService.handle(query);

        var responseList = getList.stream().map(order ->
                new OrderResponse(
                        order.getId(),
                        order.getTenantId(),
                        order.getUserId(),
                        order.getOrderNumber(),
                        order.getStatus().name(),
                        order.getCurrencyCode(),
                        order.getSubtotal(),
                        order.getDiscountTotal(),
                        order.getTotal(),
                        order.getNotes()
                )).toList();

        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Obtener todas las órdenes por ID de usuario", description = "Obtiene una lista de todas las órdenes asociadas a un ID de usuario específico dentro de un tenant")
    @GetMapping("/user/{userId}/tenant/{tenantId}")
    public ResponseEntity<List<OrderResponse>> getAllByUserId(@PathVariable UUID userId, @PathVariable UUID tenantId) {

        var query = new GetAllOrdersByUserIdQuery(tenantId, userId);

        var getList = orderQueryService.handle(query);

        var responseList = getList.stream().map(order ->
                new OrderResponse(
                        order.getId(),
                        order.getTenantId(),
                        order.getUserId(),
                        order.getOrderNumber(),
                        order.getStatus().name(),
                        order.getCurrencyCode(),
                        order.getSubtotal(),
                        order.getDiscountTotal(),
                        order.getTotal(),
                        order.getNotes()
                )).toList();

        return ResponseEntity.ok(responseList);
    }
}

