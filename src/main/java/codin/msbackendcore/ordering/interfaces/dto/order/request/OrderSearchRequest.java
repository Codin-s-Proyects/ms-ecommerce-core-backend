package codin.msbackendcore.ordering.interfaces.dto.order.request;

import codin.msbackendcore.ordering.domain.model.queries.GetOrdersByAttributesQuery;

import java.util.UUID;

public record OrderSearchRequest(
        String operation,
        UUID id,
        UUID tenantId,
        UUID userId,
        String orderNumber,
        String documentNumber,
        UUID trackingToken
) {
    public GetOrdersByAttributesQuery toQuery(){
        return new GetOrdersByAttributesQuery(
                operation,
                id,
                tenantId,
                userId,
                orderNumber,
                documentNumber,
                trackingToken
        );
    }

}
