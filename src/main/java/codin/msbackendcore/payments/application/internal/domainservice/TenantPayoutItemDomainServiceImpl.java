package codin.msbackendcore.payments.application.internal.domainservice;

import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.entities.TenantPayout;
import codin.msbackendcore.payments.domain.model.entities.TenantPayoutItem;
import codin.msbackendcore.payments.domain.services.tenantpayoutitem.TenantPayoutItemDomainService;
import codin.msbackendcore.payments.infrastructure.persistence.jpa.TenantPayoutItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TenantPayoutItemDomainServiceImpl implements TenantPayoutItemDomainService {

    private final TenantPayoutItemRepository tenantPayoutItemRepository;

    public TenantPayoutItemDomainServiceImpl(TenantPayoutItemRepository tenantPayoutItemRepository) {
        this.tenantPayoutItemRepository = tenantPayoutItemRepository;
    }

    @Override
    public TenantPayoutItem createTenantPayoutItem(TenantPayout tenantPayout, SaleCommission saleCommission, UUID orderId, BigDecimal amount) {

        var save = TenantPayoutItem.builder()
                .tenantPayoutId(tenantPayout)
                .saleCommission(saleCommission)
                .orderId(orderId)
                .amount(amount)
                .build();

        return tenantPayoutItemRepository.save(save);
    }
}
