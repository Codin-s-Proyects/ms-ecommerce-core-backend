package codin.msbackendcore.ordering.domain.services.ordershippingaddress;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderItem;
import codin.msbackendcore.ordering.domain.model.entities.OrderShippingAddress;
import jakarta.persistence.Column;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface OrderShippingAddressDomainService {
    OrderShippingAddress createOrderShippingAddress(Order order, String department, String province, String district, String addressLine, String reference, Double latitude, Double longitude,
                                                    String shippingProvider, String shippingService, BigDecimal shippingCost, Map<String, Object> providerMetadata, Instant shippedAt);
}