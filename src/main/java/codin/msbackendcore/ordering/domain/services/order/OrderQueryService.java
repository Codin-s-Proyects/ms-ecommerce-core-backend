package codin.msbackendcore.ordering.domain.services.order;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.queries.GetOrdersByAttributesQuery;

import java.util.List;

public interface OrderQueryService {
    List<Order> handle(GetOrdersByAttributesQuery query);
}
