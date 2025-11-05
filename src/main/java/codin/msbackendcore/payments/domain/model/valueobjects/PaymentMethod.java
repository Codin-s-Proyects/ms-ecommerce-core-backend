package codin.msbackendcore.payments.domain.model.valueobjects;

public enum PaymentMethod {
    CARD_VISA,
    CARD_MASTERCARD,
    CARD_AMEX,
    WALLET_YAPE,
    WALLET_PLIN,
    QR_CODE,
    BANK_TRANSFER,
    UNKNOWN;

    public static PaymentMethod fromIzipay(String type, String brand) {
        if (type == null) return UNKNOWN;

        switch (type.toUpperCase()) {
            case "CARD" -> {
                if (brand == null) return CARD_VISA;
                return switch (brand.toUpperCase()) {
                    case "VISA" -> CARD_VISA;
                    case "MASTERCARD" -> CARD_MASTERCARD;
                    case "AMEX" -> CARD_AMEX;
                    default -> CARD_VISA;
                };
            }
            case "WALLET" -> {
                if ("YAPE".equalsIgnoreCase(brand)) return WALLET_YAPE;
                if ("PLIN".equalsIgnoreCase(brand)) return WALLET_PLIN;
                return UNKNOWN;
            }
            case "QR" -> {
                return QR_CODE;
            }
            case "BANK_TRANSFER" -> {
                return BANK_TRANSFER;
            }
            default -> {
                return UNKNOWN;
            }
        }
    }
}
