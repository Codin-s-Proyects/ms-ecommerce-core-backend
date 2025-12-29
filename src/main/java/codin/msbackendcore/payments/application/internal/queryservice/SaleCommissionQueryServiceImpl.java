package codin.msbackendcore.payments.application.internal.queryservice;

import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.queries.payment.GetAllPaymentsByUserIdQuery;
import codin.msbackendcore.payments.domain.model.queries.salecommission.GetAllSaleCommissionByTenantIdQuery;
import codin.msbackendcore.payments.domain.services.payment.PaymentDomainService;
import codin.msbackendcore.payments.domain.services.payment.PaymentQueryService;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionDomainService;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleCommissionQueryServiceImpl implements SaleCommissionQueryService {

    private final SaleCommissionDomainService saleCommissionDomainService;

    public SaleCommissionQueryServiceImpl(SaleCommissionDomainService saleCommissionDomainService) {
        this.saleCommissionDomainService = saleCommissionDomainService;
    }

    @Override
    public List<SaleCommission> handle(GetAllSaleCommissionByTenantIdQuery query) {
        return saleCommissionDomainService.getAllSaleCommissionByTenantId(query.tenantId());
    }
}
