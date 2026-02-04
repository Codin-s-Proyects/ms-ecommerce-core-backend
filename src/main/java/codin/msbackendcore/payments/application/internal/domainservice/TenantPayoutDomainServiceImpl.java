package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.TenantPayout;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.tenantpayout.TenantPayoutDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.TenantPayoutRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TenantPayoutDomainServiceImpl implements TenantPayoutDomainService {

    private final TenantPayoutRepository tenantPayoutRepository;

    public TenantPayoutDomainServiceImpl(TenantPayoutRepository tenantPayoutRepository) {
        this.tenantPayoutRepository = tenantPayoutRepository;
    }

    @Override
    public TenantPayout createTenantPayout(UUID tenantId, BigDecimal totalAmount, PaymentMethod payoutMethod, String payoutReference, String payoutNotes, PaymentStatus status, UUID executedBy) {
        var save = TenantPayout.builder()
                .tenantId(tenantId)
                .totalAmount(totalAmount)
                .payoutMethod(payoutMethod)
                .payoutReference(payoutReference)
                .payoutNotes(payoutNotes)
                .status(status)
                .executedBy(executedBy)
                .build();

        return tenantPayoutRepository.save(save);
    }
}

