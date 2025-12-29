package codin.msbackendcore.payments.application.internal.queryservice;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.queries.payment.GetAllPaymentsByUserIdQuery;
import codin.msbackendcore.payments.domain.services.payment.PaymentDomainService;
import codin.msbackendcore.payments.domain.services.payment.PaymentQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentDomainService paymentDomainService;

    public PaymentQueryServiceImpl(PaymentDomainService paymentDomainService) {
        this.paymentDomainService = paymentDomainService;
    }

    @Override
    public List<Payment> handle(GetAllPaymentsByUserIdQuery query) {
        return paymentDomainService.getAllPaymentsByUserId(query.userId());
    }
}
