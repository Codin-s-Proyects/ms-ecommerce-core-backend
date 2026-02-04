package codin.msbackendcore.payments.domain.services.payment;

import codin.msbackendcore.payments.domain.model.commands.izipay.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.payment.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.payment.UpdatePaymentStatusCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.interfaces.dto.izipay.IzipayTokenResponse;
import reactor.core.publisher.Mono;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
    Payment handle(UpdatePaymentStatusCommand command);
    Mono<IzipayTokenResponse> handle(IzipayTokenPaymentCommand command);
}
