package codin.msbackendcore.iam.application.internal.dto;

import java.util.UUID;

public record SignInResult(
        UUID userId,
        String userType,
        String accessToken,
        String refreshToken,
        UUID sessionId
) {}
