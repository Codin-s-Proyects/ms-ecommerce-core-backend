package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.application.internal.valueobjects.OrderSearchOperation;
import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderChannel;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.repositories.OrderRepository;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.specification.OrderSpecification;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Order> search(OrderSearchOperation operation, UUID id, UUID tenantId, UUID userId, String orderNumber, String documentNumber, UUID trackingToken) {
        Specification<Order> spec = Specification.allOf();

        switch (operation) {
            case BY_ID -> spec = spec.and(
                    OrderSpecification.byId(id)
            );

            case BY_TENANT -> spec = spec.and(
                    OrderSpecification.byTenant(tenantId)
            );

            case BY_USER -> spec = spec.and(
                    OrderSpecification.byUser(userId)
            );

            case BY_ORDER_NUMBER -> spec = spec.and(
                    OrderSpecification.byOrderNumber(orderNumber)
            );

            case BY_DOCUMENT_NUMBER -> spec = spec.and(
                    OrderSpecification.byDocumentNumber(documentNumber)
            );


            case BY_TRACKING_TOKEN -> spec = spec.and(
                    OrderSpecification.byTrackingToken(trackingToken)
            );

            case BY_FIELDS -> {
                if (id != null)
                    spec = spec.and(OrderSpecification.byId(id));

                if (tenantId != null)
                    spec = spec.and(OrderSpecification.byTenant(tenantId));

                if (userId != null)
                    spec = spec.and(OrderSpecification.byUser(userId));

                if (orderNumber != null)
                    spec = spec.and(OrderSpecification.byOrderNumber(orderNumber));

                if (documentNumber != null)
                    spec = spec.and(OrderSpecification.byDocumentNumber(documentNumber));

                if (trackingToken != null)
                    spec = spec.and(OrderSpecification.byTrackingToken(trackingToken));

            }

            case GET_ALL -> {}

            default -> throw new BadRequestException(
                    "error.bad_request",
                    new String[]{"Invalid search operation"},
                    "operation"
            );
        }

        return orderRepository.findAll(spec);
    }
}
