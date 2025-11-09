package codin.msbackendcore.ordering.domain.services.order;

import codin.msbackendcore.ordering.domain.model.commands.order.CreateOrderCommand;
import codin.msbackendcore.ordering.domain.model.commands.order.UpdateOrderStatusCommand;
import codin.msbackendcore.ordering.domain.model.entities.Order;

public interface OrderCommandService {
    Order handle(CreateOrderCommand command);
    Order handle(UpdateOrderStatusCommand command);
}
