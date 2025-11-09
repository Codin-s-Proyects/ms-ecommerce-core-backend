package codin.msbackendcore.payments.application.internal.commandservice;

import codin.msbackendcore.payments.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.payments.application.internal.outboundservices.ExternalOrderingService;
import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.services.PaymentCommandService;
import codin.msbackendcore.payments.domain.services.PaymentDomainService;
import codin.msbackendcore.payments.infrastructure.izipay.IzipayClient;
import codin.msbackendcore.payments.interfaces.dto.IzipayTokenResponse;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateOrderNumber;
import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateTransactionId;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentDomainService paymentDomainService;
    private final ExternalCoreService externalCoreService;
    private final ExternalOrderingService externalOrderingService;

    private final IzipayClient izipayClient;

    public PaymentCommandServiceImpl(PaymentDomainService paymentDomainService, ExternalCoreService externalCoreService, ExternalOrderingService externalOrderingService, IzipayClient izipayClient) {
        this.paymentDomainService = paymentDomainService;
        this.externalCoreService = externalCoreService;
        this.externalOrderingService = externalOrderingService;
        this.izipayClient = izipayClient;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {

        //TODO: Verificar si existe el orderId

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        return paymentDomainService.createPayment(
                command.tenantId(),
                command.orderId(),
                command.amount()
        );
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

    public Payment confirmPayment(UUID tenantId, String transactionId) {
        //Map<String, Object> response = izipayClient.confirmTransaction(transactionId);
        return paymentDomainService.confirmPayment(tenantId, transactionId);
    }
}
