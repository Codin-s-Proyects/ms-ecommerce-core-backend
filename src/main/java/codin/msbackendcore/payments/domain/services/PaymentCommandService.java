package codin.msbackendcore.payments.domain.services;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.payments.domain.model.commands.CreatePaymentCommand;
import codin.msbackendcore.payments.domain.model.entities.Payment;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
}
