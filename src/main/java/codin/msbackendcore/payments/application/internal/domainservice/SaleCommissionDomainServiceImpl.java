package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.SaleCommissionRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class SaleCommissionDomainServiceImpl implements SaleCommissionDomainService {

    private final SaleCommissionRepository saleCommissionRepository;

    public SaleCommissionDomainServiceImpl(SaleCommissionRepository saleCommissionRepository) {
        this.saleCommissionRepository = saleCommissionRepository;
    }

    @Override
    public SaleCommission createSaleCommission(UUID tenantId, UUID orderId, Payment payment, UUID userId, BigDecimal grossAmount, BigDecimal commissionRate, UUID planId) {

        if (saleCommissionRepository.existsByOrderId(orderId))
            throw new BadRequestException("error.bad_request", new String[]{orderId.toString()}, "orderId");

        var commissionAmount = grossAmount.multiply(commissionRate);
        var merchantAmount = grossAmount.subtract(commissionAmount);

        var saleCommission = SaleCommission.builder()
                .tenantId(tenantId)
                .orderId(orderId)
                .payment(payment)
                .userId(userId)
                .grossAmount(grossAmount)
                .commissionAmount(commissionAmount)
                .merchantAmount(merchantAmount)
                .commissionRate(commissionRate)
                .planId(planId)
                .build();

        return saleCommissionRepository.save(saleCommission);
    }
}
