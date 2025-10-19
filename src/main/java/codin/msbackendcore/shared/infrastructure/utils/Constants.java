package codin.msbackendcore.shared.infrastructure.utils;

import java.time.Instant;

public class Constants {
    public static final Instant REFRESH_TOKEN_EXPIRES_AT = Instant.now().plusSeconds(60 * 60 * 24 * 7); // 7 days
    public static final int MAX_ACTIVE_SESSIONS = 3; // configurable
    public static final String DEFAULT_CURRENCY_CODE = "PEN";
    public static final String RETAIL_PRICE_LIST_CODE = "minorista";
    public static final String WHOLESALE_PRICE_LIST_CODE = "mayorista";
}
