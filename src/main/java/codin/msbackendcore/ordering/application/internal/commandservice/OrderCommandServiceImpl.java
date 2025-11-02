package codin.msbackendcore.ordering.application.internal.commandservice;

import codin.msbackendcore.ordering.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.ordering.domain.model.commands.order.CreateOrderCommand;
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

import java.time.Year;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateOrderNumber;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderDomainService orderDomainService;
    private final OrderCounterDomainService orderCounterDomainService;
    private final OrderItemDomainService orderItemDomainService;
    private final OrderStatusHistoryDomainService orderStatusHistoryDomainService;

    private final ExternalCoreService externalCoreService;

    public OrderCommandServiceImpl(OrderDomainService orderDomainService, OrderCounterDomainService orderCounterDomainService, OrderItemDomainService orderItemDomainService, OrderStatusHistoryDomainService orderStatusHistoryDomainService, ExternalCoreService externalCoreService) {
        this.orderDomainService = orderDomainService;
        this.orderCounterDomainService = orderCounterDomainService;
        this.orderItemDomainService = orderItemDomainService;
        this.orderStatusHistoryDomainService = orderStatusHistoryDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public Order handle(CreateOrderCommand command) {

        //TODO: Validar si el user existe
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

        for (var item : command.items()) {
            //TODO: Validar si el producto existe
            //TODO: Validar si el precio final es el correcto

            //ProductVariantData variant = catalogACL.getProductVariant(item.productVariantId());
            //BigDecimal price = pricingACL.getFinalPrice(cmd.tenantId(), item.productVariantId(), item.quantity());

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
        }

        order.addStatusHistory(orderStatusHistoryDomainService.createOrderStatusHistory(order, OrderStatus.CREATED, command.userId()));


        return orderDomainService.addItemsAndStatusHistoryToOrder(order);
    }
}
