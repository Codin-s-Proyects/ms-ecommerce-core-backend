package codin.msbackendcore.payments.domain.services;

import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.commands.IzipayTokenPaymentCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;
import codin.msbackendcore.payments.interfaces.dto.IzipayTokenRequest;
import codin.msbackendcore.payments.interfaces.dto.IzipayTokenResponse;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
    IzipayTokenResponse handle(IzipayTokenPaymentCommand command);
}
