package codin.msbackendcore.payments.application.internal.commandservice;

import codin.msbackendcore.payments.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.payments.application.internal.outboundservices.ExternalIamService;
import codin.msbackendcore.payments.application.internal.outboundservices.ExternalOrderingService;
import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.UpdatePaymentStatusCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentStatus;
import codin.msbackendcore.payments.domain.services.payment.PaymentCommandService;
import codin.msbackendcore.payments.domain.services.payment.PaymentDomainService;
import codin.msbackendcore.payments.domain.services.salecommission.SaleCommissionDomainService;
import codin.msbackendcore.payments.infrastructure.izipay.IzipayClient;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenResponse;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Year;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.*;

@Service
@Transactional
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentDomainService paymentDomainService;
    private final SaleCommissionDomainService saleCommissionDomainService;
    private final ExternalCoreService externalCoreService;
    private final ExternalOrderingService externalOrderingService;
    private final ExternalIamService externalIamService;
    private final IzipayClient izipayClient;

    public PaymentCommandServiceImpl(PaymentDomainService paymentDomainService, SaleCommissionDomainService saleCommissionDomainService, ExternalCoreService externalCoreService, ExternalOrderingService externalOrderingService, ExternalIamService externalIamService, IzipayClient izipayClient) {
        this.paymentDomainService = paymentDomainService;
        this.saleCommissionDomainService = saleCommissionDomainService;
        this.externalCoreService = externalCoreService;
        this.externalOrderingService = externalOrderingService;
        this.externalIamService = externalIamService;
        this.izipayClient = izipayClient;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {
        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        if (!externalIamService.existsUserById(command.userId(), command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.userId().toString()}, "userId");
        }

        if (!isValidEnum(PaymentStatus.class, command.paymentStatus())) {
            throw new BadRequestException("error.bad_request", new String[]{command.paymentStatus()}, "paymentStatus");
        }

        if (command.paymentMethod() != null && !isValidEnum(PaymentMethod.class, command.paymentMethod())) {
            throw new BadRequestException("error.bad_request", new String[]{command.paymentMethod()}, "paymentMethod");
        }

        if (!externalOrderingService.existOrderByIdAndTenantId(command.orderId(), command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        var payment = paymentDomainService.createPayment(
                command.tenantId(),
                command.orderId(),
                command.userId(),
                command.amount(),
                command.paymentMethod() != null ? PaymentMethod.valueOf(command.paymentMethod()) : null,
                PaymentStatus.valueOf(command.paymentStatus())
        );


        if(payment.getStatus() == PaymentStatus.CONFIRMED) {
            var plan = externalCoreService.getPlanByTenantId(payment.getTenantId());

            saleCommissionDomainService.createSaleCommission(
                    payment.getTenantId(), payment.getOrderId(), payment, payment.getUserId(), payment.getAmount(),
                    plan.commissionRate(), plan.id()
            );
        }

        return payment;
    }

    @Override
    public Payment handle(UpdatePaymentStatusCommand command) {
        if (!isValidEnum(PaymentStatus.class, command.paymentStatus())) {
            throw new BadRequestException("error.bad_request", new String[]{command.paymentStatus()}, "paymentStatus");
        }

        if (command.paymentMethod() != null && !isValidEnum(PaymentMethod.class, command.paymentMethod())) {
            throw new BadRequestException("error.bad_request", new String[]{command.paymentMethod()}, "paymentMethod");
        }

        var payment =  paymentDomainService.updatePayment(
                command.paymentId(),
                command.tenantId(),
                command.paymentMethod() != null ? PaymentMethod.valueOf(command.paymentMethod()) : null,
                PaymentStatus.valueOf(command.paymentStatus())
        );

        if(payment.getStatus() == PaymentStatus.CONFIRMED) {
            var plan = externalCoreService.getPlanByTenantId(payment.getTenantId());

            saleCommissionDomainService.createSaleCommission(
                    payment.getTenantId(), payment.getOrderId(), payment, payment.getUserId(), payment.getAmount(),
                    plan.commissionRate(), plan.id()
            );
        }

        return payment;
    }

    @Override
    public IzipayTokenResponse handle(IzipayTokenPaymentCommand command) {

        var currentYear = Year.now().getValue();
        var nextOrderNumber = externalOrderingService.getOrderCounterByTenant(command.tenantId());

        var transactionId = generateTransactionId(command.tenantId());
        var orderNumber = generateOrderNumber(currentYear, nextOrderNumber);

        String securityToken = izipayClient.generateToken(transactionId, command.amount(), orderNumber);

        return new IzipayTokenResponse(
                transactionId,
                orderNumber,
                securityToken
        );
    }
}
