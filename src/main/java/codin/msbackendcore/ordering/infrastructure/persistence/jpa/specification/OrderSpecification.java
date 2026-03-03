package codin.msbackendcore.ordering.infrastructure.persistence.jpa.specification;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class OrderSpecification {

    public static Specification<Order> withDetails() {
        return (root, query, cb) -> {
            if (query != null) {
                root.fetch("customer", JoinType.LEFT);
                root.fetch("shippingAddress", JoinType.LEFT);
                root.fetch("items", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Order> byId(UUID orderId) {
        return (root, query, cb) ->
                cb.equal(root.get("id"), orderId);
    }

    public static Specification<Order> byTenant(UUID tenantId) {
        return (root, query, cb) ->
                cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Order> byUser(UUID userId) {
        return (root, query, cb) ->
                cb.equal(root.get("userId"), userId);
    }

    public static Specification<Order> byOrderNumber(String orderNumber) {
        return (root, query, cb) ->
                cb.equal(root.get("orderNumber"), orderNumber);
    }

    public static Specification<Order> byDocumentNumber(String documentNumber) {
        return (root, query, cb) -> {
            Join<Object, Object> customer = root.join("customer");
            return cb.equal(customer.get("documentNumber"), documentNumber);
        };
    }

    public static Specification<Order> byTrackingToken(UUID token) {
        return (root, query, cb) ->
                cb.equal(root.get("trackingToken"), token);
    }

}
