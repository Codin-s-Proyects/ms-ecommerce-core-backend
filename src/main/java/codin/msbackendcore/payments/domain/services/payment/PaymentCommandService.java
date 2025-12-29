package codin.msbackendcore.payments.domain.services.payment;

import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.UpdatePaymentStatusCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenResponse;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
    Payment handle(UpdatePaymentStatusCommand command);
    IzipayTokenResponse handle(IzipayTokenPaymentCommand command);
}
