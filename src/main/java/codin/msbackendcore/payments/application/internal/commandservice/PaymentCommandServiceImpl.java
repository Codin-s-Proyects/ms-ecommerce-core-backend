package codin.msbackendcore.payments.application.internal.commandservice;

import codin.msbackendcore.payments.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.domain.model.valueobjects.PaymentMethod;
import codin.msbackendcore.payments.domain.services.PaymentCommandService;
import codin.msbackendcore.payments.domain.services.PaymentDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentDomainService paymentDomainService;
    private final ExternalCoreService externalCoreService;

    public PaymentCommandServiceImpl(PaymentDomainService paymentDomainService, ExternalCoreService externalCoreService) {
        this.paymentDomainService = paymentDomainService;
        this.externalCoreService = externalCoreService;
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
}
