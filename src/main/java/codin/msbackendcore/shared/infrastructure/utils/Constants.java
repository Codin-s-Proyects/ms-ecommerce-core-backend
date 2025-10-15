package codin.msbackendcore.shared.infrastructure.utils;

import java.time.Instant;

public class Constants {
    public static final Instant REFRESH_TOKEN_EXPIRES_AT = Instant.now().plusSeconds(60 * 60 * 24 * 7); // 7 days
    public static final int MAX_ACTIVE_SESSIONS = 3; // configurable
}
