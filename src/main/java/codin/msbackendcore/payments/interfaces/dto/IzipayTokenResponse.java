package codin.msbackendcore.payments.interfaces.dto;

public record IzipayTokenResponse(
        String transactionId,
        String orderNumber,
        String token
) {
}
