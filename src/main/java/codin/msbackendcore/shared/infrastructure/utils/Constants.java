package codin.msbackendcore.shared.infrastructure.utils;

import java.time.Instant;

public class Constants {
    public static final Instant REFRESH_TOKEN_EXPIRES_AT = Instant.now().plusSeconds(60 * 60 * 24 * 30); // 30 days
}
