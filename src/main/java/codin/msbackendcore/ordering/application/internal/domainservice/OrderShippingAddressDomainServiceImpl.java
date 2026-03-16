package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderShippingAddress;
import codin.msbackendcore.ordering.domain.services.ordershippingaddress.OrderShippingAddressDomainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
public class OrderShippingAddressDomainServiceImpl implements OrderShippingAddressDomainService {

    @Override
    public OrderShippingAddress createOrderShippingAddress(Order order, String department, String province, String district, String addressLine, String reference, Double latitude, Double longitude,
                                                           String shippingProvider, String shippingService, BigDecimal shippingCost, Map<String, Object> providerMetadata, Instant shippedAt) {
        return OrderShippingAddress.builder()
                .order(order)
                .department(department)
                .province(province)
                .district(district)
                .addressLine(addressLine)
                .reference(reference)
                .latitude(latitude)
                .longitude(longitude)
                .shippingProvider(shippingProvider)
                .shippingService(shippingService)
                .shippingCost(shippingCost)
                .providerMetadata(providerMetadata)
                .shippedAt(shippedAt)
                .build();

    }
}
