package codin.msbackendcore.payments.application.internal.commandservice;

import codin.msbackendcore.payments.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.payments.application.internal.outboundservices.ExternalIamService;
import codin.msbackendcore.payments.application.internal.outboundservices.ExternalOrderingService;
import codin.msbackendcore.payments.domain.model.commands.izipay.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.payment.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.payment.UpdatePaymentStatusCommand;
import codin.msbackendcore.payments.domain.model.commands.tenantpayout.CreateTenantPayoutCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import codin.msbackendcore.payments.domain.model.entities.TenantPayout;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.payment.PaymentCommandService;
import codin.msbackendcore.payments.domain.services.payment.PaymentDomainService;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionDomainService;
import codin.msbackendcore.payments.domain.services.tenantpayout.TenantPayoutCommandService;
import codin.msbackendcore.payments.domain.services.tenantpayout.TenantPayoutDomainService;
import codin.msbackendcore.payments.domain.services.tenantpayoutitem.TenantPayoutItemDomainService;
import codin.msbackendcore.payments.infrastructure.izipay.IzipayClient;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenResponse;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.*;

@Service
@Transactional
public class TenantPayoutCommandServiceImpl implements TenantPayoutCommandService {

    private final TenantPayoutDomainService payoutDomainService;
    private final TenantPayoutItemDomainService paymentItemDomainService;
    private final SaleCommissionDomainService saleCommissionDomainService;

    private final ExternalCoreService externalCoreService;

    public TenantPayoutCommandServiceImpl(TenantPayoutDomainService payoutDomainService, TenantPayoutItemDomainService paymentItemDomainService, SaleCommissionDomainService saleCommissionDomainService, ExternalCoreService externalCoreService) {
        this.payoutDomainService = payoutDomainService;
        this.paymentItemDomainService = paymentItemDomainService;
        this.saleCommissionDomainService = saleCommissionDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public TenantPayout handle(CreateTenantPayoutCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var commissions = saleCommissionDomainService.getAllUnpaidSaleCommissionsByTenantId(command.tenantId());

        if (commissions.isEmpty())
            throw new BadRequestException("error.no_pending_commissions", null, "tenantId");

        BigDecimal totalAmount = commissions.stream()
                .map(SaleCommission::getMerchantAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var tenantPayout = payoutDomainService.createTenantPayout(
                command.tenantId(),
                totalAmount,
                PaymentMethod.valueOf(command.payoutMethod()),
                command.payoutReference(),
                command.payoutNotes(),
                PaymentStatus.valueOf(command.status()),
                command.executedBy()
        );

        for (SaleCommission commission : commissions) {

            paymentItemDomainService.createTenantPayoutItem(
                    tenantPayout,
                    commission,
                    commission.getOrderId(),
                    commission.getMerchantAmount()
            );

            saleCommissionDomainService.markCommissionsAsPaid(commission);
        }

        return tenantPayout;

    }
}
