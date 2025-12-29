package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.payment.PaymentDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.PaymentRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateTransactionId;

@Service
public class PaymentDomainServiceImpl implements PaymentDomainService {

    private final PaymentRepository paymentRepository;

    public PaymentDomainServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(UUID tenantId, UUID orderId, UUID userId, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {

        var confirmedAt = paymentStatus.equals(PaymentStatus.CONFIRMED) ? Instant.now() : null;

        var payment = Payment.builder()
                .tenantId(tenantId)
                .orderId(orderId)
                .userId(userId)
                .paymentMethod(paymentMethod)
                .status(paymentStatus)
                .transactionId(generateTransactionId(tenantId))
                .amount(amount)
                .confirmedAt(confirmedAt)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(UUID paymentId, UUID tenantId, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        var payment = paymentRepository.findByIdAndTenantId(paymentId, tenantId)
                .orElseThrow(() -> new BadRequestException("error.bad_request", new String[]{paymentId.toString()}, "paymentId"));

        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(paymentStatus);
        if(paymentStatus.equals(PaymentStatus.CONFIRMED)) {
            payment.setConfirmedAt(Instant.now());
        }

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPaymentsByUserId(UUID userId) {
        return paymentRepository.findAllByUserId(userId);
    }
}
