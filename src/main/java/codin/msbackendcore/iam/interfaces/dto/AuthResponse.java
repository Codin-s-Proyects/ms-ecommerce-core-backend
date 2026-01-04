package codin.msbackendcore.iam.interfaces.dto;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        UUID sessionId,
        String accessToken,
        String refreshToken,
        String userType
) {}