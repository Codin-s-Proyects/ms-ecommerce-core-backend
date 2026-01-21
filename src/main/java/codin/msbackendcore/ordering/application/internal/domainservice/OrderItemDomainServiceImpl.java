package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderItem;
import codin.msbackendcore.ordering.domain.services.orderitem.OrderItemDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.repositories.OrderItemRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderItemDomainServiceImpl implements OrderItemDomainService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemDomainServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(UUID tenantId, Order order, UUID productVariantId, String productName, String sku, Map<String, Object> attributes, Integer quantity, BigDecimal unitPrice, BigDecimal discountPercent) {

        if (orderItemRepository.existsByOrderAndTenantIdAndProductVariantId(order, tenantId, productVariantId))
            throw new BadRequestException("error.already_exist", new String[]{productVariantId.toString()}, "productVariantId");

        return OrderItem.builder()
                .tenantId(tenantId)
                .order(order)
                .productVariantId(productVariantId)
                .productName(productName)
                .sku(sku)
                .attributes(attributes)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .discountPercent(discountPercent)
                .build();
    }
}
