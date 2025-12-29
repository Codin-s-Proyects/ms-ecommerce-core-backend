package codin.msbackendcore.payments.interfaces.dto.izipay;

public record IzipayTokenResponse(
        String transactionId,
        String orderNumber,
        String token
) {
}
