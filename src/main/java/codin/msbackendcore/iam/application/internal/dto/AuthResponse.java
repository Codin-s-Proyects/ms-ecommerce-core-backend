package codin.msbackendcore.iam.application.internal.dto;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String accessToken,
        String refreshToken,
        String userType
) {}