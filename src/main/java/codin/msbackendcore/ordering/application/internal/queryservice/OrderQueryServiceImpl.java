package codin.msbackendcore.ordering.application.internal.queryservice;

import codin.msbackendcore.ordering.application.internal.valueobjects.OrderSearchOperation;
import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.queries.GetOrdersByAttributesQuery;
import codin.msbackendcore.ordering.domain.services.order.OrderDomainService;
import codin.msbackendcore.ordering.domain.services.order.OrderQueryService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderDomainService orderDomainService;

    public OrderQueryServiceImpl(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }

    @Override
    public List<Order> handle(GetOrdersByAttributesQuery query) {

        if (!isValidEnum(OrderSearchOperation.class, query.operation())) {
            throw new BadRequestException("error.bad_request", new String[]{query.operation()}, "operation");
        }

        return orderDomainService.search(
                OrderSearchOperation.valueOf(query.operation()),
                query.id(),
                query.tenantId(),
                query.userId(),
                query.orderNumber(),
                query.documentNumber(),
                query.trackingToken()
        );
    }
}
