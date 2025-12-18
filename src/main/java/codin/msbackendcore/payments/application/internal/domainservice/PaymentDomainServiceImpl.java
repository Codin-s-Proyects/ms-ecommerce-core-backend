package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.PaymentDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.PaymentRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateTransactionId;

@Service
public class PaymentDomainServiceImpl implements PaymentDomainService {

    private final PaymentRepository paymentRepository;

    public PaymentDomainServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(UUID tenantId, UUID orderId, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {

        var confirmedAt = paymentStatus.equals(PaymentStatus.CONFIRMED) ? Instant.now() : null;

        var payment = Payment.builder()
                .tenantId(tenantId)
                .orderId(orderId)
                .paymentMethod(paymentMethod)
                .status(paymentStatus)
                .transactionId(generateTransactionId(tenantId))
                .amount(amount)
                .confirmedAt(confirmedAt)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment confirmPayment(UUID tenantId, String transactionId) {
        Payment payment = paymentRepository.findByTransactionIdAndTenantId(transactionId, tenantId)
                .orElseThrow(() -> new BadRequestException("error.bad_request", new String[]{transactionId}, "transactionId"));

        payment.setStatus(PaymentStatus.CONFIRMED);
        payment.setConfirmedAt(Instant.now());

        return paymentRepository.save(payment);
    }

    @Override
    public Payment failPayment(UUID tenantId, String transactionId) {
        Payment payment = paymentRepository.findByTransactionIdAndTenantId(transactionId, tenantId)
                .orElseThrow(() -> new BadRequestException("error.bad_request", new String[]{transactionId}, "transactionId"));

        payment.setStatus(PaymentStatus.FAILED);
        payment.setConfirmedAt(Instant.now());

        return paymentRepository.save(payment);
    }
}
