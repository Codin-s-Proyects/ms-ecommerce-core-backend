package codin.msbackendcore.iam.interfaces.dto;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String accessToken,
        String refreshToken,
        String userType
) {}