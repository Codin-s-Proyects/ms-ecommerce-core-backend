package codin.msbackendcore.ordering.domain.services.ordercustomer;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderCustomer;
import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;

public interface OrderCustomerDomainService {
    OrderCustomer createOrderCustomer(Order order, String firstName, String lastName, String email, String phone, DocumentType documentType, String documentNumber);
}
