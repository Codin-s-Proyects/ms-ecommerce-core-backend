package codin.msbackendcore.ordering.domain.model.valueobjects;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    PENDING_SHIPMENT,
    SHIPPED,
    DELIVERED,
    COMPLETED,
    CANCELED,
    RETURNED
}
