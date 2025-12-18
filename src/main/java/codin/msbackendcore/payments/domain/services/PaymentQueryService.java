package codin.msbackendcore.payments.domain.services;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;

import java.util.List;

public interface PaymentQueryService {
    List<Payment> handle(GetAllPaymentsByUserIdQuery query);
}
