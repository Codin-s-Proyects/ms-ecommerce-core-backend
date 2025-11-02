package codin.msbackendcore.ordering.domain.services.order;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByTenantIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetAllOrdersByUserIdQuery;
import codin.msbackendcore.ordering.domain.model.queries.GetOrderByIdQuery;

import java.util.List;

public interface OrderQueryService {
    Order handle(GetOrderByIdQuery query);

    List<Order> handle(GetAllOrdersByTenantIdQuery query);

    List<Order> handle(GetAllOrdersByUserIdQuery query);
}
