package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.Order;
import codin.msbackendcore.ordering.domain.model.entities.OrderCustomer;
import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;
import codin.msbackendcore.ordering.domain.services.ordercustomer.OrderCustomerDomainService;
import org.springframework.stereotype.Service;

@Service
public class OrderCustomerDomainServiceImpl implements OrderCustomerDomainService {

    @Override
    public OrderCustomer createOrderCustomer(Order order, String firstName, String lastName, String email, String phone, DocumentType documentType, String documentNumber) {
        return OrderCustomer.builder()
                .order(order)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .documentType(documentType)
                .documentNumber(documentNumber)
                .build();
    }
}
