package codin.msbackendcore.iam.interfaces.dto;

import codin.msbackendcore.iam.domain.model.commands.RefreshTokenCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record RefreshTokenRequest(
        @NotBlank String refreshToken,
        @NotNull UUID userId,
        @NotNull UUID sessionId,
        @NotBlank String identifier,
        @NotBlank String userType
) {
    public RefreshTokenCommand toCommand() {
        return new RefreshTokenCommand(
                userId,
                sessionId,
                refreshToken,
                identifier,
                userType
        );
    }
}