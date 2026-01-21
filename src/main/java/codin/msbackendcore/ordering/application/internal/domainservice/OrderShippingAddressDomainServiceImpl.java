package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderShippingAddress;
import codin.msbackendcore.ordering.domain.services.ordershippingaddress.OrderShippingAddressDomainService;
import org.springframework.stereotype.Service;

@Service
public class OrderShippingAddressDomainServiceImpl implements OrderShippingAddressDomainService {

    @Override
    public OrderShippingAddress createOrderShippingAddress(Order order, String department, String province, String district, String addressLine, String reference, Double latitude, Double longitude) {
        return OrderShippingAddress.builder()
                .order(order)
                .department(department)
                .province(province)
                .district(district)
                .addressLine(addressLine)
                .reference(reference)
                .latitude(latitude)
                .longitude(longitude)
                .build();

    }
}
