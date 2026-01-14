package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderChannel;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.OrderRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderDomainServiceImpl implements OrderDomainService {

    private final OrderRepository orderRepository;

    public OrderDomainServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Order createOrder(UUID tenantId, UUID userId, String orderNumber, String currencyCode, BigDecimal subtotal, BigDecimal discountTotal, BigDecimal total, OrderChannel orderChannel, String notes) {

        if (orderRepository.existsByOrderNumberAndTenantId(orderNumber, tenantId))
            throw new BadRequestException("error.already_exist", new String[]{orderNumber}, "orderNumber");

        var order = Order.builder()
                .tenantId(tenantId)
                .userId(userId)
                .orderNumber(orderNumber)
                .status(OrderStatus.CREATED)
                .currencyCode(currencyCode)
                .subtotal(subtotal)
                .discountTotal(discountTotal)
                .total(total)
                .notes(notes)
                .orderChannel(orderChannel)
                .build();

        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order updateOrderStatus(UUID tenantId, UUID orderId, OrderStatus orderStatus) {
        if (!orderRepository.existsByIdAndTenantId(orderId, tenantId))
            throw new BadRequestException("error.not_found", new String[]{orderId.toString()}, "orderId");

        var order = orderRepository.findByIdWithStatusHistory(orderId)
                .orElseThrow(() -> new BadRequestException("error.not_found", new String[]{orderId.toString()}, "orderId"));

        order.setStatus(orderStatus);

        return orderRepository.save(order);
    }

    @Override
    public Order persistOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(UUID orderId, UUID tenantId) {
        return orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new BadRequestException("error.not_found", new String[]{orderId.toString()}, "orderId"));
    }

    @Override
    public List<Order> getOrdersByTenantId(UUID tenantId) {
        return orderRepository.findByTenantId(tenantId);
    }

    @Override
    public List<Order> getOrdersByUserId(UUID userId, UUID tenantId) {
        return orderRepository.findByUserIdAndTenantId(userId, tenantId);
    }
}
