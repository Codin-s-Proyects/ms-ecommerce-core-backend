package codin.msbackendcore.ordering.interfaces.rest.controller;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.services.order.OrderCommandService;
import codin.msbackendcore.ordering.domain.services.order.OrderQueryService;
import codin.msbackendcore.ordering.interfaces.dto.order.request.OrderCreateRequest;
import codin.msbackendcore.ordering.interfaces.dto.order.request.OrderSearchRequest;
import codin.msbackendcore.ordering.interfaces.dto.order.request.OrderStatusUpdateRequest;
import codin.msbackendcore.ordering.interfaces.dto.order.response.OrderCustomerResponse;
import codin.msbackendcore.ordering.interfaces.dto.order.response.OrderResponse;
import codin.msbackendcore.ordering.interfaces.dto.order.response.OrderShippingAddressResponse;
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

        var response = entityToResponse(saved);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Actualizar el estado de una orden", description = "Actualiza el estado de una orden existente utilizando su ID")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest req, @PathVariable UUID orderId) {

        var command = req.toCommand(orderId);

        var saved = orderCommandService.handle(command);

        var response = entityToResponse(saved);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una orden o varias por atributos", description = "Obtiene una orden o varias órdenes basadas en diferentes atributos de búsqueda")
    @PostMapping("/search")
    public ResponseEntity<List<OrderResponse>> getOrdersByAttributes(@RequestBody OrderSearchRequest searchRequest) {

        var query = searchRequest.toQuery();

        var order = orderQueryService.handle(query);

        return ResponseEntity.ok(
                order.stream().map(this::entityToResponse).toList()
        );
    }

    private OrderResponse entityToResponse(Order order) {

        var customer = new OrderCustomerResponse(
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                order.getCustomer().getEmail(),
                order.getCustomer().getPhone(),
                order.getCustomer().getDocumentType().name(),
                order.getCustomer().getDocumentNumber()
        );

        var shippingAddress = new OrderShippingAddressResponse(
                order.getShippingAddress().getDepartment(),
                order.getShippingAddress().getProvince(),
                order.getShippingAddress().getDistrict(),
                order.getShippingAddress().getAddressLine(),
                order.getShippingAddress().getReference()
        );

        return new OrderResponse(
                order.getId(),
                order.getTenantId(),
                order.getUserId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getCurrencyCode(),
                order.getSubtotal(),
                order.getDiscountTotal(),
                order.getTotal(),
                order.getNotes(),
                order.getOrderChannel().name(),
                order.getTrackingToken(),
                customer,
                shippingAddress,
                order.getCreatedAt()
        );
    }
}

