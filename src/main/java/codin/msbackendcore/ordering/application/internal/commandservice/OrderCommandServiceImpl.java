package codin.msbackendcore.ordering.application.internal.commandservice;

import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalCatalogService;
import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalIamService;
import codin.msbackendcore.ordering.domain.model.commands.order.CreateOrderCommand;
import codin.msbackendcore.ordering.domain.model.commands.order.UpdateOrderStatusCommand;
import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.valueobjects.OrderStatus;
import codin.msbackendcore.ordering.domain.services.order.OrderCommandService;
import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.domain.services.ordercounter.OrderCounterDomainService;
import codin.msbackendcore.ordering.domain.services.orderitem.OrderItemDomainService;
import codin.msbackendcore.ordering.domain.services.orderstatushistory.OrderStatusHistoryDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateOrderNumber;
import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Transactional
@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderDomainService orderDomainService;
    private final OrderCounterDomainService orderCounterDomainService;
    private final OrderItemDomainService orderItemDomainService;
    private final OrderStatusHistoryDomainService orderStatusHistoryDomainService;

    private final ExternalCoreService externalCoreService;
    private final ExternalCatalogService externalCatalogService;
    private final ExternalIamService externalIamService;

    public OrderCommandServiceImpl(OrderDomainService orderDomainService, OrderCounterDomainService orderCounterDomainService, OrderItemDomainService orderItemDomainService, OrderStatusHistoryDomainService orderStatusHistoryDomainService, ExternalCoreService externalCoreService, ExternalCatalogService externalCatalogService, ExternalIamService externalIamService) {
        this.orderDomainService = orderDomainService;
        this.orderCounterDomainService = orderCounterDomainService;
        this.orderItemDomainService = orderItemDomainService;
        this.orderStatusHistoryDomainService = orderStatusHistoryDomainService;
        this.externalCoreService = externalCoreService;
        this.externalCatalogService = externalCatalogService;
        this.externalIamService = externalIamService;
    }

    @Override
    public Order handle(CreateOrderCommand command) {

        if (!externalIamService.existsUserById(command.userId(), command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.userId().toString()}, "userId");
        }

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var currentYear = Year.now().getValue();
        var nextOrderNumber = orderCounterDomainService.getOrderCounterByTenant(command.tenantId());

        var orderNumber = generateOrderNumber(currentYear, nextOrderNumber);

        var order = orderDomainService.createOrder(
                command.tenantId(),
                command.userId(),
                orderNumber,
                command.currencyCode(),
                command.subtotal(),
                command.discountTotal(),
                command.total(),
                command.notes()
        );

        var orderFinalPrice = BigDecimal.ZERO;

        for (var item : command.items()) {
            if (!externalCatalogService.existsProductVariantById(item.productVariantId())) {
                throw new BadRequestException("error.bad_request", new String[]{item.productVariantId().toString()}, "productVariantId");
            }

            var orderItem = orderItemDomainService.createOrderItem(
                    command.tenantId(),
                    order,
                    item.productVariantId(),
                    item.productName(),
                    item.sku(),
                    item.attributes(),
                    item.quantity(),
                    item.unitPrice(),
                    item.discountPercent()
            );

            order.addItem(orderItem);

            var discountAmount = item.unitPrice().multiply(item.discountPercent());
            var itemFinalPrice = item.unitPrice().subtract(discountAmount).multiply(BigDecimal.valueOf(item.quantity()));
            orderFinalPrice = orderFinalPrice.add(itemFinalPrice);
        }

        if (orderFinalPrice.compareTo(command.total()) != 0)
            throw new BadRequestException("error.bad_request", new String[]{"Order total does not match sum of item prices"}, "total");

        order.addStatusHistory(orderStatusHistoryDomainService.createOrderStatusHistory(order, OrderStatus.CREATED, command.userId()));

        for (var item : order.getItems()) {
            externalCatalogService.reserve(
                    item.getProductVariantId(),
                    command.tenantId(),
                    item.getQuantity()
            );
        }

        return orderDomainService.persistOrder(order);
    }

    @Override
    public Order handle(UpdateOrderStatusCommand command) {
        if (!isValidEnum(OrderStatus.class, command.status()))
            throw new BadRequestException("error.bad_request", new String[]{command.status()}, "status");

        var order = orderDomainService.updateOrderStatus(
                command.tenantId(),
                command.orderId(),
                OrderStatus.valueOf(command.status())
        );

        order.addStatusHistory(orderStatusHistoryDomainService.createOrderStatusHistory(order, OrderStatus.valueOf(command.status()), order.getUserId()));

        if (order.getStatus().equals(OrderStatus.CANCELED) || (order.getStatus().equals(OrderStatus.RETURNED))) {
            for (var item : order.getItems()) {
                externalCatalogService.release(item.getProductVariantId(), command.tenantId(), item.getQuantity());
            }
        } else if (order.getStatus().equals(OrderStatus.PAID)) {
            for (var item : order.getItems()) {
                externalCatalogService.confirm(item.getProductVariantId(), command.tenantId(), item.getQuantity());
            }
        }

        return orderDomainService.persistOrder(order);
    }
}
